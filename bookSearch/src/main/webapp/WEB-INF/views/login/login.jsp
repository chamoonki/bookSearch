<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
	   	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	   	<meta name="description" content="">
	   	<meta name="author" content="">
	   
	   	<title>Book Search Login</title>
	   	
	   	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	   	<link rel="stylesheet" type="text/css" href="/css/style.css">
	   	
	   	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	   	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	   	
	</head>
	<body class="login">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<form class="form-signin" action="/login" method="POST">
						<!-- Login Title -->
			          	<h2 class="form-signin-heading" align="center">Book Search for Kakao</h2>
			          	
			          	<!-- 계정 Label & Input -->
			          	<div class="form-group">
						    <label for="exampleInputEmail1">Account</label>
						    <input type="text" class="form-control" id="inputAcount" name="account" placeholder="Enter Account" required autofocus>
						</div>
			          	
			          	<!-- 패스워드 Label & Input -->
			          	<div class="form-group">
						    <label for="exampleInputPassword1">Password</label>
							<input type="password" class="form-control" id="inputPassword" name="passwd" placeholder="Password" required>
						</div>
			          	
			          	<!-- 에러 텍스트 -->	
			          	<div class="alert alert-${alertType} alert-dismissible fade ${alertShow}" role="alert">
			          	<!-- Account information is incorrect.
							<strong>ID </strong>, please check <strong>PASSWD</strong> again. -->
						  ${resultMessage}
						  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
						    <span aria-hidden="true">&times;</span>
						  </button>
						</div>
			          	<%-- <div class="error-message alert text-danger ${resultCode}" style="">계정정보가 정확하지 않습니다. 다시 입력해주세요.</div> --%>
			          	
			          	<!-- 로그인 & 회원가입 버튼 -->
						<div class="row">
							<div class="col-lg-12" align="center">
								<button class="btn btn-lg btn-outline-primary" type="submit">Login </button>
								<button class="btn btn-lg btn-outline-info" id="membership" type="button">membership</button>
							</div>
						</div>
						
						<hr>
						<ul class="list-group text-muted">
						    <li class="list-group-item">👀 <strong>처음 사용자</strong>는 계정과 패스워드를 입력하고 로그인 하시면됩니다.</li>
						    <li class="list-group-item">👀 <strong> 기존 사용자</strong>는 처음에 입력하신 계정과 패스워드를 입력하고 로그인 하시면됩니다.</li>
						</ul>
			        </form>
				</div>
			</div>
			
			<!-- Modal -->
			<div class="modal fade" id="membershipModal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="ModalLabel">Book Search Membership</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <form class="form-signin" action="/login/membership" method="POST">
			      <div class="modal-body">			          	
			          	<!-- 계정 Label & Input -->
			          	<div class="form-group">
						    <label for="exampleInputEmail1">Account</label>
						    <input type="text" class="form-control" id="regAcount" name="account" placeholder="Enter Account" required autofocus>
						</div>
			          	
			          	<!-- 패스워드 Label & Input -->
			          	<div class="form-group">
						    <label for="exampleInputPassword1">Password</label>
							<input type="password" class="form-control" id="regPassword" name="passwd" placeholder="Password" required>
						</div>	        
			      </div>
			      <div class="modal-footer">
			      	<button type="submit" class="btn btn-primary">Save changes</button>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>
			      </form>
			    </div>
			  </div>
			</div>
		</div>
	</body>
	
	<script type="text/javascript">
		// 회원 가입 Modal Open
		$("#membership").unbind("click").bind("click",function(){
			//console.log('#############');
			$("#membershipModal").modal();
		})
   	</script>
</html>