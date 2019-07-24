<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>책 검색 Service</title>
		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	   	<link rel="stylesheet" type="text/css" href="/css/style.css">
	   	
	   	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	   	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	   	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	</head>
	<body>
		<!-- Top Division 부분 -->
		<div style="background: #442c2a;color: white;padding: 21px 0px;">
			<div class="container">
				<%@ include file="/include/top.jsp"%>
				<!-- <h1>Book Search</h1> -->
				<div class="row">
					<div class="col-lg-12">
						<form id="search_form" name="search_form">
							<input type="hidden" name="page" value="1">
							<div class="form-row">
								<div class="col-lg-5">
									<div class="form-group">
										<label for="select-target">Book Target Field</label>
										<select class="form-control" name="target">
											<c:forEach var="target" items="${BookTarget}">
												<option value="${target.code}">${target}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-lg-6">
									<div class="form-group">
										<label for="input-searchword">Search word</label>
										<input id="input-searchword" class="form-control" name="keyword" placeholder="Enter search term.">
									</div>
								</div>
								<div class="col-lg-1">
									<div class="form-group">
										<label>&nbsp;</label>
										<div>
											<button class="btn btn-primary" type="button" onClick="loadMore(1)">
												<div class="spinner-border" role="status" style="width: 24px; height: 24px; display: none">
												  <span class="sr-only">Loading...</span>
												</div>
												<span class="search-text">
													Search
												</span>
												 <!-- Search -->
											</button>
										</div>
									</div>
								</div>
							</div>
						</form>
						
					</div>
				</div>
			</div>
		</div>
		
		<!-- Main Contents Division 부분 -->
		<div class="container">
			<div class="row" style="padding-top: 20px;">
				<div class="col-lg-12">
					<!-- portlet -->
					<div style="padding: 0; border: 1px solid #e7ecf1 !important; background-color: #fff; margin-top: 0; margin-bottom: 25px;">
						<!-- title -->
						<div style="padding: 15px 20px 10px; border-bottom: 1px solid #eef1f5; min-height: 70px; margin-bottom: 10px;">
							<div style="color: #666; padding: 10px 0; float: left; display: inline-block; font-size: 25px; line-height: 18px;">
								<span style="color: #2f353b !important; text-transform: uppercase !important; font-weight: 700 !important">
									Book List
								</span>
								<span style="padding: 0; margin: 0; line-height: 13px; color: #9eacb4; font-size: 16px; font-weight: 400;">
									You can view book information about what you search for.
								</span>
							</div>
						</div>
						<!-- body -->
						<!-- 결과가 있을 때의 Body -->
						<div style="padding: 10px 20px 20px; clear: both; display:none;" id="book_body"></div>
						<!-- 결과가 없을 때의 Body -->
						<div style="padding: 10px 20px 20px; clear: both;" id="book_no_body">
							<div class="conteiner">
								<div class="row">
									<div class="col-lg-12">
										<div id="noResult" class="alert" style="text-align:center;">
											<img src="./assets/img/alert.svg" style="width: 30px;">
											Oops! No books have been searched. Please check again.
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="./js/utils.js"></script>
	<script type="text/javascript">
		var page = 1, inc = 0;
		
		// search_form 에 대해 Submit 버튼을 눌렀을 때 발생 하는 Event
		$("#search_form").submit(function( event ) {
			event.preventDefault();

			// form & page 등 변수 설정을 해준다.
			var pg = page ? page : 1;
			var frm = document.search_form;
			frm.page.value = pg;

			// keyword가 없을 경우 Alert 메세지 출력
			if (frm.keyword.value == "") {
				alert("Enter search word.");
			}else{	// keyword가 있을 경우 Ajax를 실행 시켜 준다.
				var options = {url : "/search", method: "GET", data : $(frm).serialize()}
				ajaxHelper(options,
					function(){	// beforeSend
						$(frm).find("button[type=submit]").prop("disabled", true);
						$(frm).find("button[type=submit] > .spinner-border").css("display", "block");
						$(frm).find("button[type=submit] > .search-text").css("display", "none");
					},
					function(res){	// success
						// 검색 한 Book Data를 저장 시켜 준다.
						jsonRes = JSON.parse(res.bookData);
						
						// 가져온 데이터가 있는지 없는지 판단 한다.
						// 가져온 데이터가 없을 경우 데이터 없다는 메세지를 보여 준다.
						if (jsonRes.meta.total_count < 1) {
							// 가져온 값이 비어 있기 때문에 그에 맞게 Contents를 변경 해 준다.
							isEmpty(true);
							
						} else {	// 만약 데이터가 있다면 html 을 그려 준다.
							// 가져온 값이 있기 때문에 그에 맞게 Contents를 변경 해 준다.
							isEmpty(false);
								
							var html = "";
							// 가져온 Book documents를 돌면서 데이터를 가져와 준다.
							(jsonRes.documents).forEach(function (obj, idx){
								
								var arr_isbn = obj.isbn.split(' ');
								
								// 4개의 Contens 마다 row를 append 해준다.
								if(!(idx % 4)){
									$("#book_body").append('<div class="row row-books books' + (inc++) + '" style="padding: 12px 0;"></div>');
								}

								// book Contents를 row에 append 해준다.
								$("#book_body > .books" + (inc - 1)).append(
									'<div class="col-lg-3">'+
										'<div style="color:#fff; min-height: 250px; box-sizing: border-box;">'+
							                '<div style="position: relative; box-sizing: border-box;">'+
							                    '<img src="' + obj.thumbnail + '" style="height: 250px; width: 100%; vertical-align: middle; border: 0;"> </div>'+
							                '<div style="width: 125px; position: absolute; right: 15px; top: 0; min-height: 250px; background: rgba(142,68,173,.8)!important">'+
							                    '<div style="text-align: center; margin-top: 20px; padding: 10px; font-size: 12px;">'+ 
													// Detail은 가져온 정보를 토대로 popup을 띄워 준다.
						                    		'<a href="#" onclick="detailView(' + ((arr_isbn.length > 1) ? arr_isbn[1] : arr_isbn[0]) +')">'+
							                    	(obj.title + ' | '+ obj.publisher) + 
							                    	'</a>'+
							                    '</div>'+
							                    '<div style="text-align: left; padding-left: 5px; font-size: 11px;">저자: ' + obj.authors + '</div>'+
							                    '<div style="text-align: left; padding-left: 5px; font-size: 11px;">번역: ' + obj.trans + '</div>'+
							                    '<div style="text-align: left; padding-left: 5px; font-size: 11px;">상태: ' + obj.status + '</div>'+
							                    '<div style="margin-top: 30px; padding-left: 5px; position: absolute; right: 0;">'+
							                        '<button type="button" class="btn btn-circle btn-danger btn-sm" style="width: 90px;border-top-right-radius: 0 !important; border-bottom-right-radius: 0 !important; border: none !important;">'+
														'Bookmark'+
							                        '</button>'+
							                    '</div>'+
							                '</div>'+
							            '</div>'+
									'</div>'
								);
 								
							});

							// 가져온 데이터 Page의 끝인지를 판단 한다.
							if (!jsonRes.meta.is_end) {
								$("#book_body").append(
									'<button class="btn btn-primary btn-lg btn-block" onclick="loadMore(); $(this).remove();"> Load More </button>'
								);
							}
							/* $(jsonRes.documents).each(function(idx) {
								console.log('######### = ', idx);
								var authors = "", trans = "", thumbnail = "", isbn = "";

								$(this.authors).each(function() {
									authors += (this + " ")
								});
								$(this.translators).each(function() {
									trans += (this + " ")
								});

								if (this.thumbnail) {
									thumbnail = "<img src='"+this.thumbnail+"' width='100'>";
								}

								var arr_isbn = this.isbn.split(" ");
								

								if(arr_isbn.length>1){
									isbn = arr_isbn[1];
								}else{
									isbn = arr_isbn[0];
								}

								html += "<li class='list-group-item'>";
								html += "<dl><dt><a href='./detail?isbn="
										+ isbn
										+ "'>"
										+ this.title
										+ " | "
										+ this.publisher
										+ "</a>"
										+" <button type='button' class='btn btn-primary btn-bookmark add' data-isbn='"+isbn+"'>북마크</button>"
										+"</dt>";
								html += "<dd><div class='left'>"
										+ thumbnail
										+ "</div><div class='right'>저자: "
										+ authors
										+ "<br> 번역자: "
										+ trans
										+ "<br> 상태: "
										+ this.status
										+ "</div></dd></dl></li>";
											});
							if (!jsonRes.meta.is_end) {
								html += "<li><button class='btn btn-primary btn-lg btn-block' onclick='submitSearch("
										+ (pg + 1)
										+ "); $(this).parent().remove();'>더보기 </button></li>";
							}
							if (pg > 1) {
								$("#books > ul").append(html);
							} else {
								$("#books > ul").html(html);
							}
						}
						console.log(res); */
						}
					},	
					function(err){	// error & fail
						console.log(err);
						alert(err);
					},
					function(){	// complete
						$(frm).find("button[type=submit]").prop("disabled", false);
						$(frm).find("button[type=submit] > .spinner-border").css("display", "none");
						$(frm).find("button[type=submit] > .search-text").css("display", "block");
					}
				);
			}
		});

		// Detail View를 보여주기 위한 함수
		function detailView(isbn){
			console.log('##################!!!!! = ', isbn);
			ajaxHelper(
				// ajax info
				{url:"/detailView", method: "GET", data: {isbn: isbn}},
				function(){	// beforeSend
				},
				function(res){	// success
					console.log('######### = ', res);
				},	
				function(){	// error & fail
				},
				function(){	// complete
					
				}
			);	
		}

		// LoadMore 버튼을 클릭 했을 때 들어 오는 함수.
		function loadMore(setPage){
			// setPage가 있을 경우 Page를 다시 1로 세팅해 준다.
			if(setPage)
				page = setPage;
			else	// 없을 경우 page를 1번 증가 시켜 준다.
				page++;

			// BookSearch 호출
			$('#search_form').submit();
		}
		
		// Search 후 가져온 데이터가 있는지 판단 후에 Contents를 변경 시켜 준다.
		function isEmpty(type){
			// Page가 1이라면 이전 Data를 삭제 시켜 준다.
			if(page === 1){
				$("#book_body").html("");
				inc = 0;
			}
				
			
			if(type){
				// 페이지를 다시 1로 만들어 준다.
				page = 1;
				$("#book_body").hide();
				// Message를 보여 준다.
				$("#book_no_body").show();
			}else{
				// Message를 보여 준다.
				$("#book_no_body").hide();
				$("#book_body").show();
			}
		}
		
		// load 되었을 때 시작
		$(document).ready(function(){
		   $(".nav-icon").tooltip();
		});
		
	</script>
</html>