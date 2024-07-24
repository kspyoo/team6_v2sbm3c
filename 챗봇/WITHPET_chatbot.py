from openai import OpenAI
import os
import cx_Oracle  # Oracle
import pandas as pd
import numpy as np
import json
import random

from flask import Flask, request, render_template, jsonify
from flask_cors import CORS

client = OpenAI(
  api_key=os.getenv('OPENAI_API_KEY')
)

# db에서 장소 이름과 컬럼의 정보를 찾아서 제공
def get_place_info_from_db(place_name, info_type, conn):
    cursor = conn.cursor() # SQL 실행 객체 생성
    cursor.execute("SELECT * FROM culturefacility WHERE CNAME LIKE :name",name=f'{place_name}')
    place_info = cursor.fetchone()
    if not place_info:
        return None
    
    conn.close()
    
    columns = ["행번호", "시설 이름", "주소, 위치", "위도", "경도", "우편번호", "전화번호, 연락처, 번호", "휴일, 쉬는날, 휴무일", "영업시간", "주차 가능여부", "시설 분류", "홈페이지", "그외 정보"]
    place_dict = dict(zip(columns, place_info))
    result = ''
    for key in place_dict.keys():
        print(key)
        if info_type in key:
            return place_dict[key]
        else:
            result = None
            
    return result

# ai를 통해 답변내용을 정리해서 제공
def generate_ai_response(user_query, format):
    # OpenAI API를 사용하여 질문을 자연어로 이해합니다.
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
              {
                  'role': 'system',
                  'content': f'{user_query}를 정리해서 출력 해주세요'
              },
              {
                  'role': 'user',
                  'content': user_query + '\n\n출력 형식(json): ' + format
              }
          ],
        n=1,             # 응답수, 다양한 응답 생성 가능
        max_tokens=1000, # 응답 생성시 최대 1000개의 단어 사용
        temperature=0.5,   # 창의적인 응답여부, 값이 클수록 확률에 기반한 창의적인 응답이 생성됨
        response_format= { "type":"json_object" }
    )
    return json.loads(response.choices[0].message.content)

# 사용자의 질문에서 장소이름과 원하는 정보(컬럼)을 추출
def extract_place_name_and_info(query_text,format):
    # AI 모델을 사용하여 질의에서 장소 이름과 정보 타입을 추출합니다.
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
              {
                  'role': 'system',
                  'content': '다음 질문에서 장소 이름 알기 원하는 것을 찾아서 추출해 주세요'
              },
              {
                  'role': 'user',
                  'content': query_text + '\n\n출력 형식(json): ' + format
              }
          ],
        n=1,             # 응답수, 다양한 응답 생성 가능
        max_tokens=1000, # 응답 생성시 최대 1000개의 단어 사용
        temperature=0,   # 창의적인 응답여부, 값이 클수록 확률에 기반한 창의적인 응답이 생성됨
        response_format= { "type":"json_object" }
    )
    
    return json.loads(response.choices[0].message.content)

# 질문의 의도를 구분(추천 or 정보찾기)
def get_question_type(query_text,format):
    # AI 모델을 사용하여 질의에서 다음 질문의 의도를 추출합니다.
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
              {
                  'role': 'system',
                  'content': '다음 질문의 의도를 파악해 주세요. 결과는 추천 또는 정보찾기 또는 알수없음으로 출력해주세요"'
              },
              {
                  'role': 'user',
                  'content': query_text + '\n\n출력 형식(json): ' + format
              }
          ],
        n=1,             # 응답수, 다양한 응답 생성 가능
        max_tokens=1000, # 응답 생성시 최대 1000개의 단어 사용
        temperature=0,   # 창의적인 응답여부, 값이 클수록 확률에 기반한 창의적인 응답이 생성됨
        response_format= { "type":"json_object" }
    )
    
    return json.loads(response.choices[0].message.content)

# 추천 받고싶어하는 질문에서 지역의 이름과 시설의 종류를 추출
def get_locationName(query_text,format):
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
              {
                  'role': 'system',
                  'content': '다음 질문에서 지역 이름과 원하는 장소의 타입("반려동물용품","미용","카페","식당","동물약국","박물관","여행지","펜션","미술관","위탁관리","동물병원","문예회관" 중에서 하나)을 추출해주세요.'
              },
              {
                  'role': 'user',
                  'content': query_text + '\n\n출력 형식(json): ' + format
              }
          ],
        n=1,             # 응답수, 다양한 응답 생성 가능
        max_tokens=1000, # 응답 생성시 최대 1000개의 단어 사용
        temperature=0,   # 창의적인 응답여부, 값이 클수록 확률에 기반한 창의적인 응답이 생성됨
        response_format= { "type":"json_object" }
    )
    
    return json.loads(response.choices[0].message.content)

# db에서 지역이름과 카테고리를 like 조건을 통해 추출함
def get_place_recommend_db(location_name, place_type, conn):
    cursor = conn.cursor() # SQL 실행 객체 생성
    cursor.execute("SELECT count(*) as cnt FROM culturefacility WHERE RADDRESS LIKE :name and CULTURECATE LIKE :type",
                   name=f'%{location_name}%', type=f'%{place_type}%')
    place_count = cursor.fetchone()
    columns = ["cnt"]
    count = dict(zip(columns, place_count))
    
    if count['cnt'] != 0 :
        randow_row = random.randrange(0,count['cnt'])

        cursor.execute("SELECT * FROM culturefacility WHERE RADDRESS LIKE :name and CULTURECATE LIKE :type",
                        name=f'%{location_name}%', type=f'%{place_type}%')

        place_info = cursor.fetchall()
        conn.close()

        randow_row = random.randrange(0,count['cnt'])

        columns = ["행번호", "시설 이름", "주소, 위치", "위도", "경도", "우편번호", "전화번호", "휴일", "영업시간", "주차 가능여부", "시설 분류", "홈페이지", "그외 정보"]    
        place_dict = dict(zip(columns, place_info[randow_row]))

        if place_dict:
            return place_dict
            
    return None

app = Flask(__name__)  # __name__ == '__main__'
CORS(app)

@app.post("/chatbot") # http://localhost:5000/chatbot
def chatbot_proc():
    data = request.json
    # f=request.files['file']

    question = data['question']
    
    # Oracle Connection 연결, kd 계정으로 XE 사용.
    # conn = cx_Oracle.connect('kd/1234@localhost:1521/XE')
    conn = cx_Oracle.connect('team6/1234@15.164.40.179:1521/XE', encoding='UTF-8')
    
    user_query = question

    format = '''
        {
        "type" : "추천 or 정보찾기"
        }
    '''
    
    response = get_question_type(user_query, format)
    question_type = response['type']
    print('질문의도 : ', question_type)

    if question_type == '정보찾기':
        format = '''
            {
            "name" : "장소 이름",
            "type" : "원하는 것"
            }
        '''
        response = extract_place_name_and_info(user_query, format)

        place_name = response['name']
        info_type = response['type']
        print('장소와 궁금한 정보 : ',place_name, info_type)

        format = '''
            {
                "res" : "정리한 문장"
            }
        '''
        if place_name and info_type:  
            info_value = get_place_info_from_db(place_name, info_type,conn)
            print(info_value)
            if info_value:
                ai_response = generate_ai_response(f"{place_name}의 {info_type}은/는 {info_value}입니다.",format)
            else:
                ai_response = generate_ai_response(f"'{place_name}'에 대한 '{info_type}' 정보를 찾을 수 없습니다.", format)
        else:
            ai_response = generate_ai_response(f'질문을 이해하지 못했습니다. 다시 질문해 주세요', format)
    elif question_type == '추천':
        format = '''
            {
            "name" : "지역이름",
            "type" : "원하는 장소의 타입"
            }
        '''
        
        answer = get_locationName(user_query, format)

        location_name = answer['name']
        placte_type = answer['type']

        print('지역 이름과 추천받고싶은 종류', location_name, placte_type)
        answer = get_place_recommend_db(location_name,placte_type, conn);
        
        format='''
            {
                "res" : "정리한 문장"
            }
        '''
        
        if answer:
            ai_response = generate_ai_response(f'저는 {answer["주소, 위치"]}에 있는 {answer["시설 이름"]}을 추천드려요', format)
        else:
            ai_response = generate_ai_response(f'죄송합니다. {location_name}에 있는 {placte_type}을(를) 찾지 못했습니다. 다시 검색해 주세요', format)

        print('response : ', ai_response['res'])
    else:
        format='''
            {
                "res" : "정리한 문장"
            }
        '''
        ai_response = generate_ai_response(f'질문을 이해하지 못했습니다. 다시 질문해 주세요', format)

    
    obj = {
        "response": f'{ai_response["res"]}'
    }
    
    return jsonify(obj) # dictionary -> json string
    

app.run(host="0.0.0.0", port=5000, debug=True)  # 0.0.0.0: 모든 Host 에서 접속 가능, python llama_chatbot.py
