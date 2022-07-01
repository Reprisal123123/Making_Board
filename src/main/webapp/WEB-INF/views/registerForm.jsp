<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="java.net.URLDecoder"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <style>
        * { box-sizing:border-box; }

        form {
            width:400px;

            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(89,117,196);
            border-radius: 10px;
        }

        .input-field {
            width: 300px;
            height: 30px;
            border : 1px solid rgb(89,117,196);
            border-radius:10px;
            padding: 0 10px;
            margin-bottom: 10px;
        }
        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }

        button {
            background-color: white;
            color : rgb(89,117,196);
            width:120px;
            height:40px;
            font-size: 17px;
            border : 2px solid rgb(89,117,196);
            border-radius: 5px;
            margin : 20px 0 30px 0;

        }

        button:hover {
            background-color: rgb(89,117,196);
            color: white;
        }

        .title {
            font-size : 50px;
            margin: 40px 0 30px 0;
        }

        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            font-weight: bold;
            color:red;
            margin-bottom: 5px;
        }
        .sns-chk {
            margin-top : 5px;
        }

        #idCheck {
            background-color: white;
            color : rgb(89,117,196);
            width:90px;
            height:30px;
            font-size: 13px;
            border : 2px solid rgb(89,117,196);
            border-radius: 5px;
            margin : 5px 0 5px 0;
        }

        #idCheck:hover {
            background-color: rgb(89,117,196);
            color: white;
        }

        #pwdCheck {
            background-color: white;
            color : rgb(89,117,196);
            width:110px;
            height:30px;
            font-size: 13px;
            border : 2px solid rgb(89,117,196);
            border-radius: 5px;
            margin : 5px 0 5px 0;
        }

        #pwdCheck:hover {
            background-color: rgb(89,117,196);
            color: white;
        }

    </style>
    <title>Register</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<script>
    let msg = "${msg}";
    if(msg=="REG_ERR") alert("회원가입에 실패하였습니다. 다시 시도해 주세요.");
</script>
<!-- form action="<c:url value="/register/add"/>" method="POST" onsubmit="return formCheck(this)"-->
<form:form modelAttribute="userDto">
    <div class="title">Register</div>
    <div id="msg" class="msg"><form:errors path="id"/></div>
    <div id="msg" class="msg"><form:errors path="pwd"/></div>
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" value="<c:out value='${userDto.id}'/>" placeholder="8~12자리의 영대소문자와 숫자 조합" autofocus>
    <button id="idCheck" type="button">아이디 체크</button>
    <label for="">비밀번호</label>
    <input class="input-field" type="password" name="pwd" value="<c:out value='${userDto.pwd}'/>" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <button id="pwdCheck" type="button">비밀번호 체크</button>
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" value="<c:out value='${userDto.name}'/>" placeholder="홍길동">
    <label for="">이메일</label>
    <input class="input-field" type="email" name="email" value="<c:out value='${userDto.email}'/>" placeholder="example@fastcampus.co.kr">
    <label for="">생일</label>
    <input class="input-field" type="date" name="birth">
    <div class="sns-chk">
        <label><input type="checkbox" name="sns" value="facebook"/>페이스북</label>
        <label><input type="checkbox" name="sns" value="kakaotalk" checked/>카카오톡</label>
        <label><input type="checkbox" name="sns" value="instagram"/>인스타그램</label>
    </div>
    <button>회원 가입</button>
</form:form>
<script>
    function formCheck(frm) {
        let msg ='';
        if(frm.id.value.length<3) {
            setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
            return false;
        }
        return true;
    }
    function setMessage(msg, element){
        document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;
        if(element) {
            element.select();
        }
    }


    $(document).ready(function() {

        $("#idCheck").click(function(){
            var id = $("input[name=id]").val();
            var checkResult = null;

            if(id.trim()=='') {
                alert("id를 입력해주세요.")
                $("input[name=id]").focus()
                return;
            } else {
                if(id.length <= 7 || id.length >= 13) {
                    alert("id의 길이는 8~12자리여야 합니다.")
                    $("input[name=id]").focus()
                    return;
                }
            }

            $.ajax({
                type: 'POST',
                url: '/ch6/idcheck',
                headers: {"content-type": "application/json"},
                data: id,
                success: function (result) {
                    checkResult = JSON.parse(result);
                    if (checkResult == true) {
                        alert("사용 가능한 아이디 입니다.");
                    } else {
                        alert("중복되는 아이디 입니다.");
                    }
                },
                error: function () {
                    alert("error")
                }
            }); // $.ajax()
        });

        $("#pwdCheck").click(function(){
            var pwd = $("input[name=pwd]").val();
            var checkResult = null;

            if(pwd.trim()=='') {
                alert("비밀번호를 입력해주세요.")
                $("input[name=pwd]").focus()
                return;
            } else {
                if(pwd.length <= 7 || pwd.length >= 13) {
                    alert("비밀번호의 길이는 8~12자리여야 합니다.")
                    $("input[name=pwd]").focus()
                    return;
                }
            }

            $.ajax({
                type: 'POST',
                url: '/ch6/pwdcheck',
                headers: {"content-type": "application/json"},
                data: pwd,
                success: function (result) {
                    checkResult = JSON.parse(result);
                    if (checkResult == true) {
                        alert("사용 가능한 비밀번호 입니다.");
                    } else {
                        alert("8~12자리의 비밀번호를 설정하세요");
                    }
                },
                error: function () {
                    alert("error")
                }
            }); // $.ajax()
        });
    })

</script>
</body>
</html>