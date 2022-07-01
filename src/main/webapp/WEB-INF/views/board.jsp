<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        .container {
            width : 1000px;
            height: auto;
            margin : auto;
        }
        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }
        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }
        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }
        .btn:hover {
            text-decoration: underline;
        }

        #commentList {
            margin-top : 20px;
            margin-bottom : 20px;
        }

        #commentList > ul {
            background-color: white;
            width: 100%;
            height: auto;
            display: inline;
        }
        #commentList > ul > li {
            display: flex;
            flex-wrap: wrap;
            margin: 7px;
            color: black;
            width: auto;
            height: auto;
            font-size: 13px;
        }

        #commentList > ul > li > span {
            margin : 5px;
        }

        #commentList > ul > li > button {
            background-color: white;
            color : black;
            width:40px;
            height:30px;
            font-size: 13px;
            border : 2px solid black;
            border-radius: 5px;
            margin : 5px 5px;
        }
        #commentList > ul > li > button:hover {
            background-color: black;
            color: white;
        }

        .commenter {
            width : 110px;
            border: 1px solid black;
            text-align: center;
            font-weight: bold;
            font-color: rgb(34, 34, 34);
        }

        .comment {
            width : 58%;
            border: 1px solid black;
            font-size: 13px;
        }

        #replyForm {
            display: none;
            width: 100%;
            align-items: center;
            margin: 20px 0;
        }
        #replyForm > textarea {
            width: 75%;
            height: 70px;
            margin: 0;
            font-size: 13px;
        }

        #modForm {
            display: none;
            width: 100%;
            align-items: center;
            margin: 20px 0;
        }

        #modForm > textarea {
            width: 75%;
            height: 70px;
            margin: 0;
            font-size: 13px;
        }

        #commentForm {
            display: flex;
            align-items: center;
            margin-bottom : 20px;
        }
        .writer {
            border: 1px solid black;
            background-color: #e0f8eb;
            margin-right: 5px;
            width: 15%;
            height: 70px;
            text-align: center;
            line-height: 70px;
            font-size: 15px;
            font-weight: bold;
        }
        #commentForm > textarea {
            width: 75%;
            height: 70px;
            margin: 0;
            font-size: 13px;
        }
        .sendComment {
            display: flex;
            width: 85px;
            justify-content: center;
            align-items: center;
        }
        #modBtn, #sendCommentBtn, #sendRepBtn {
            background-color: white;
            color : black;
            width:40px;
            height:30px;
            font-size: 13px;
            border : 2px solid black;
            border-radius: 5px;
            margin : 5px 0 5px 0;
        }
        #modBtn:hover, #sendCommentBtn:hover, #sendRepBtn:hover {
            background-color: black;
            color: white;
        }


    </style>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">ReMinD</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li id="loginid"><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<script>
    let msg = "${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
    <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" class="frm" action="" method="post">
        <input type="hidden" name="bno" value="${boardDto.bno}">

        <input name="title" type="text" value="<c:out value='${boardDto.title}'/>" placeholder=" 제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
        <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><c:out value="${boardDto.content}"/></textarea><br>

        <c:if test="${mode eq 'new'}">
            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
        </c:if>
        <c:if test="${mode ne 'new'}">
            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
        </c:if>
        <c:if test="${boardDto.writer eq loginId}">
            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
        </c:if>
        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
    </form>
    <div id="commentList"></div>
    <div id="replyForm">
        <div class="writer">${sessionScope.id}</div>
        <textarea name="repComment" placeholder="내용을 입력하세요" rows="6" cols="30"></textarea>
        <div class="sendComment"><button id="sendRepBtn" type="button">등록</button></div>
    </div>
    <div id="modForm">
        <div class="writer">${sessionScope.id}</div>
        <textarea name="modComment" placeholder="내용을 입력하세요" rows="6" cols="30"></textarea>
        <div class="sendComment"><button id="modBtn" type="button">수정</button></div>
    </div>
    <c:if test="${mode ne 'new'}">
        <div id="commentForm">
            <div class="writer">${sessionScope.id}</div>
            <textarea name="wrtComment" placeholder="내용을 입력하세요" rows="6" cols="30"></textarea>
            <div class="sendComment"><button id="sendCommentBtn" type="button">등록</button></div>
        </div>
    </c:if>

</div>
<script>
    let bno = '${boardDto.bno}';

    let addZero = function(value){
        return value > 9 ? value : "0"+value;
    }

    let dateToString = function(ms) {
        let date = new Date(ms);

        let yyyy = date.getFullYear();
        let mm = addZero(date.getMonth() + 1);
        let dd = addZero(date.getDate());

        let HH = addZero(date.getHours());
        let MM = addZero(date.getMinutes());
        let ss = addZero(date.getSeconds());

        return yyyy+"."+mm+"."+dd+ " " + HH + ":" + MM + ":" + ss;
    }

    let toHtml = function (comments) {
        let tmp = "<ul>";

        comments.forEach(function(comment) { // list<CommentDto>에서 CommentDto를 하나씩 꺼내 comment에 담음
            tmp += '<li data-cno='+ comment.cno
            tmp += ' data-pcno=' + comment.pcno
            tmp += ' data-bno=' + comment.bno + '>'
            if(comment.cno!=comment.pcno)
                tmp+= 'ㄴ'
            tmp += ' <span class="commenter">' + comment.commenter + '</span>'
            tmp += ' <span class="comment">' + comment.comment + '</span>'
            // tmp += comment.up_date
            tmp += dateToString(comment.up_date)
            if(comment.commenter=="${sessionScope.id}") {
                tmp += '<button class="modBtn">수정</button>'
                tmp += '<button class="delBtn">삭제</button>'
            }
            tmp += '<button class="replyBtn">답글</button>'
            tmp += '</li>'
        })

        return tmp + "</ul>";
    }

    let showList = function(bno) {
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/ch6/comments?bno='+bno,  // 요청 URI
            success : function(result){
                $('#commentList').html(toHtml(result)); // 서버로부터 응답이 도착하면 호출될 함수
            },
            error   : function(){ alert("error...") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }

    $(document).ready(function(){
        showList(bno);

        let formCheck = function() {
            let form = document.getElementById("form");
            if(form.title.value=="") {
                alert("제목을 입력해 주세요.");
                form.title.focus();
                return false;
            }
            if(form.content.value=="") {
                alert("내용을 입력해 주세요.");
                form.content.focus();
                return false;
            }
            return true;
        }
        $("#writeNewBtn").on("click", function(){
            location.href="<c:url value='/board/write'/>";
        });
        $("#writeBtn").on("click", function(){
            let form = $("#form");
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#modifyBtn").on("click", function(){
            let form = $("#form");
            let isReadonly = $("input[name=title]").attr('readonly');
            // 1. 읽기 상태이면, 수정 상태로 변경
            if(isReadonly=='readonly') {
                $(".writing-header").html("게시판 수정");
                $("input[name=title]").attr('readonly', false);
                $("textarea").attr('readonly', false);
                $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                $("#commentList").html("");
                $("#commentForm").html("");
                return;
            }
            // 2. 수정 상태이면, 수정된 내용을 서버로 전송
            form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#removeBtn").on("click", function(){
            if(!confirm("해당 글을 삭제하시겠습니까?")) return;
            let form = $("#form");
            form.attr("action", "<c:url value='/board/remove${searchCondition.queryString}'/>");
            form.attr("method", "post");
            form.submit();
        });
        $("#listBtn").on("click", function(){
            location.href="<c:url value='/board/list${searchCondition.queryString}'/>";
        });

        // 댓글 작성
        $("#sendCommentBtn").on("click", function() {
            let comment = $("textarea[name=wrtComment]").val();

            if(comment.trim()=='') {
                alert("내용을 입력해주세요.");
                $("textarea[name=wrtComment]").focus()
                return;
            }
            $.ajax({
                type:'POST',
                url: '/ch6/comments/?bno='+bno,
                headers: {"content-type" : "application/json"}, // 요청 헤더
                data : JSON.stringify({bno:bno, comment:comment}), // 서버로 전송할 데이터, stringify()로 직렬화 필요.
                success : function(result) {
                    alert(result);
                    showList(bno);
                },
                error : function() {
                    alert("댓글 작성 실패")
                }
            }); // $.ajax()

            $("textarea[name=wrtComment]").val('')
        });

        // 댓글 삭제 버튼
        // $(".delBtn").click(function() {
        $("#commentList").on("click", ".delBtn", function (){
            if(!confirm("해당 댓글을 삭제하시겠습니까?")) return;

            let bno = $(this).parent().attr("data-bno");
            let cno = $(this).parent().attr("data-cno");

            $.ajax({
                type:'DELETE',       // 요청 메서드
                url: '/ch6/comments/'+cno+'?bno='+bno,  // 요청 URI
                success : function(result){
                    alert(result);
                    showList(bno);
                },
                error   : function(){ alert("error...") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

        // 댓글 수정 버튼 눌렀을 때
        $("#commentList").on("click", ".modBtn", function() {
            // 수정 버튼을 다시 눌렀을 때 Form이 사라지도록 함
            let modForm = $("#modForm").css("display");
            if(modForm=="flex") {
                $("#modForm").css("display", "none");
                return;
            }

            let cno = $(this).parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent()).text();

            // 1. modForm을 옮기고
            $("#modForm").appendTo($(this).parent());
            // 2. 답글을 입력할 폼을 보여주고
            $("#modForm").css("display", "flex");

            // 1. comment의 내용을 input에 뿌려주기
            $("textarea[name=modComment]").val(comment);
            // 2. cno 전달하기
            $("#modBtn").attr("data-cno", cno);

        });

        // 댓글 수정 완료 버튼
        $("#modBtn").on("click", function () {
            let cno = $(this).attr("data-cno");
            let comment = $("textarea[name=modComment]").val();

            if(comment.trim()=='') {
                alert("내용을 입력해주세요.");
                $("textarea[name=comment]").focus()
                return;
            }
            $.ajax({
                type:'PATCH',
                url: '/ch6/comments/'+cno,
                headers: {"content-type" : "application/json"}, // 요청 헤더
                data : JSON.stringify({cno:cno, comment:comment}), // 서버로 전송할 데이터, stringify()로 직렬화 필요.
                success : function(result) {
                    alert(result);
                    showList(bno);
                },
                error : function() {
                    alert("error1")
                }
            }); // $.ajax()

            $("#modForm").css("display", "none")
            $("input[name=modComment]").val('');
            $("#modForm").appendTo("body");
        });

        // 답글 버튼 눌렀을 때
        $("#commentList").on("click", ".replyBtn", function (){
            // 답글 버튼을 다시 눌렀을 때 form이 사라지도록 함
            let replyForm = $("#replyForm").css("display");
            if(replyForm=="flex") {
                $("#replyForm").css("display", "none");
                return;
            }

            // 1. replyForm을 옮기고
            $("#replyForm").appendTo($(this).parent());
            // 2. 답글을 입력할 폼을 보여주고
            $("#replyForm").css("display", "flex");

            // // 3. pcno가 될 cno 전달하기
            // let pcno = $(this).parent().attr("data-cno");
            // $("#sendRepBtn").attr("data-pcno", pcno);

        });

        // 답글 등록 눌렀을 때
        $("#sendRepBtn").click(function() {
            let comment = $("textarea[name=repComment]").val();
            let pcno = $("#replyForm").parent().attr("data-cno");
            // let pcno = $("#sendRepBtn").attr("data-pcno");

            if(comment.trim()=='') {
                alert("내용을 입력해주세요.");
                $("input[name=repComment]").focus()
                return;
            }

            $.ajax({
                type:"POST",
                url: '/ch6/comments?bno='+bno, // 요청 URI // /ch6/comments?bno=1338 POST
                headers : {"content-type": "application/json"}, // 요청 헤더
                data : JSON.stringify({pcno:pcno, bno:bno, comment:comment}), // 서버로 전송할 데이터,
                success : function(result) {
                    alert(result);
                    showList(bno);
                },
                error : function() { alert("rep error")}
            });

            $("#replyForm").css("display", "none")
            $("textarea[name=repComment]").val('')
            $("#replyForm").appendTo("body");
        });

    });

</script>
</body>
</html>
