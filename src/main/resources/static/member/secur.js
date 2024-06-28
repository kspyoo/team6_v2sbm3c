export function secur(obj) {
  if (obj.value.length > 0) {
    var bool = true;
    // 특수문자 검사
    var expText = /[%=<>]/;
    if (expText.test(obj.value)) {
      alert("특수문자는 입력할 수 없습니다.");
      obj.value = obj.value.replace(expText, "");
      return false;
    }

    // SQL 키워드 검사
    var sql = ["OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC", "UNION", "FETCH", "DECLARE", "TRUNCATE"];

    for (var i = 0; i < sql.length; i++) {
      var regex = new RegExp(sql[i], "gi");

      if (regex.test(obj.value)) {
        alert(`"${sql[i]}"와 같은 특정문자는 입력할 수 없습니다.`);
        obj.value = obj.value.replace(regex, "");
        return false;
      } 
    }
  }
  return true;
  
}
