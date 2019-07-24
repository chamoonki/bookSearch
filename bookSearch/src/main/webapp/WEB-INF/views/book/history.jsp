<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//	System.out.println(historyData);

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>책 검색 Service</title>
		
		
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		<script type="text/javascript" src="/js/jquery.bootpag.min.js"></script>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	   	<link rel="stylesheet" type="text/css" href="/css/style.css">
	</head>
	<body>
		<!-- Top Division 부분 -->
		<div class="top-header">
			<div class="container">
				<%@ include file="/include/top.jsp"%>
			</div>
		</div>
		
		<!-- Body Division 부분 -->
		<div class="container">
			<div class="row pt-20">
				<div class="col-lg-12">
					<div class="portlet">
						<!-- title -->
						<div class="portlet-header">
							<div class="portlet-label">
								<span class="portlet-title">
									Search History
								</span>
								<span class="portlet-title-desc">
									You can check the information you have retrieved so far.
								</span>
							</div>
						</div>
						<!-- body -->
						<!-- 결과가 있을 때의 Body -->
						<c:if test="${not empty historyData.content }">
							<div class="portlet-no-body">
								<div class="row">
									<div class="col-lg-6">
										<form action="./history" id="sortForm" name="sortForm" method="get" class="form-inline">
											<input type="hidden" name="page" value="${historyData.number}">
											<input type="hidden" name="size" value="${historyData.size }">
											<div class="form-row mb-4">
											    <label for="select-sort" class="col-sm-6 col-form-label">Regist Date </label>
											    <div class="col-sm-6">
											      <select id="select-sort" name="sort" class="form-control">
														<option value="regdate,desc" ${param.sort=='regdate,desc'?'selected="selected"':'' }>DESC </option>
														<option value="regdate,asc" ${param.sort=='regdate,asc'?'selected="selected"':'' }>ASC </option>
													</select>
											    </div>
											</div>
										</form>
									</div>
									<div class="col-lg-6">
										<div id="paging-layout-top" class="paging-layout center-block" style="float: right"></div>
									</div>
								</div>
								
								
								<ul class="list-group">
									<c:if test="${empty historyData.content }">
										<li>empty data.</li>
									</c:if>
									<c:forEach var="data" items="${historyData.content }">
										<li class="list-group-item"><dl>
												<dt>
													<img src="./assets/img/keyword.svg" style="width: 20px;">
													Search Keyword: ${data.keyword }
												</dt>
												<dd>
													<div class="row">
														<div class="col-lg-6">
															<span style="padding-left: 30px;">Target: ${data.target}</span>
														</div>
														<div class="col-lg-6">
															<span class="text-right" style="float: right;">
																<img src="./assets/img/calendar.svg" style="width: 20px;">
																<fmt:formatDate value="${data.regdate }" pattern="yyyy. MM. dd HH:mm:ss"/>
															</span>
														</div>
													</div>
												</dd>
											</dl></li>
									</c:forEach>
								</ul>
								<div class="row">
									<div class="col-lg-12">
										<div id="paging-layout" class="paging-layout center-block" style="float: right;"></div>
									</div>
								</div>
							</div>
							
						</c:if>
						<!-- 결과가 없을 때의 Body -->
						<c:if test="${empty historyData.content}">
							<div class="portlet-no-body">
								<div class="conteiner">
									<div class="row">
										<div class="col-lg-12">
											<div id="noResult" class="alert" style="text-align:center;">
												<img src="./assets/img/alert.svg" style="width: 30px;">
												History search result is missing.
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</body>
	
	<script type="text/javascript">
		$('#sortForm select[name=sort]').on('change',function(){
			var frm = document.sortForm
			frm.page.value = 0;
			frm.submit();
		});

		// 최초에 들어오는 함수
		$(document).ready(function() {
			// history Data가 있을 경우 보여 준다
			<c:if test="${not empty historyData.content }">
				 $('#paging-layout, #paging-layout-top').bootpag({
				    total: ${historyData.totalPages},
				    page: ${historyData.number+1},
				    maxVisible: 10,
				     leaps: true,
				    firstLastUse: true,
				    first: '←',
				    last: '→', 
				    wrapClass: 'pagination',
				    activeClass: 'active',
				    disabledClass: 'disabled',
				    nextClass: 'next',
				    prevClass: 'prev',
				    lastClass: 'last',
				    firstClass: 'first' 
				}).on("page", function(event, num){
					console.log(num);
					var frm = document.sortForm
					frm.page.value = num-1;
					frm.submit();
				}); 
			</c:if> 
		});
	</script>
</html>