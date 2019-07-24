<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>책 검색 Service</title>
		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	   	<link rel="stylesheet" type="text/css" href="/css/style.css">
	   	
	   	<script src="https://kit.fontawesome.com/e022ee3f50.js"></script>
	   	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	   	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	   	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	</head>
	<body>
		<!-- Top Division 부분 -->
		<div class="top-header">
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
									<label for="input-searchword">Search word</label>
									<div class="input-group">
										
										<input id="input-searchword" class="form-control" name="keyword" placeholder="Enter search term." aria-label="Text input with dropdown button">
										<div class="input-group-append">
										    <button class="btn btn-outline-secondary dropdown-toggle popular" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										    	<i class="fa fa-star"></i>
										    </button>
										    <div class="dropdown-menu">
										    	<a class="dropdown-item disabled">Popular Keyword</a>
										    	<div role="separator" class="dropdown-divider"></div>
										    	<span class="dropdown-item disabled pop-no-data">
										    		<img class="w-px-14" src="./assets/img/alert.svg">
										    		 There are no popular keywords.
										    	</span>
										    	<div class="popular-data">
										    		<a class="dropdown-item disabled w-px-12">1. Popular Keyword <span class="badge badge-success ml-2">9</span></a>
										    	</div>
										    	
										    </div>
										  </div>
									</div>
								</div>
								<div class="col-lg-1">
									<div class="form-group">
										<label>&nbsp;</label>
										<div>
											<button class="btn btn-primary" type="button" onClick="loadMore(1)">
												<div class="spinner-border search-loading" role="status" >
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
			<div class="row pt-20">
				<div class="col-lg-12">
					<!-- portlet -->
					<div class="portlet">
						<!-- title -->
						<div class="portlet-header" >
							<div class="portlet-label">
								<span class="portlet-title">
									Book List
								</span>
								<span class="portlet-title-desc">
									You can view book information about what you search for.
								</span>
							</div>
						</div>
						<!-- body -->
						<!-- 결과가 있을 때의 Body -->
						<div class="portlet-body" id="book_body"></div>
						<!-- 결과가 없을 때의 Body -->
						<div class="portlet-no-body" id="book_no_body">
							<div class="conteiner">
								<div class="row">
									<div class="col-lg-12">
										<div id="noResult" class="alert" style="text-align:center;">
											<img class="w-px-30" src="./assets/img/alert.svg">
											 No books have been searched. Please check again.
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Detail Modal -->
		<%@ include file="/include/tpl/detail-modal.jsp"%>
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
										'<div class="book-card">'+
							                '<div class="book-card-header">'+
							                    '<img class="book-card-thumbnail" src="' + (obj.thumbnail ? obj.thumbnail : './assets/img/default-book.svg') + '"> </div>'+
							                /* '<div style="width: 125px; position: absolute; right: 15px; top: 0; min-height: 250px; background: rgba(47,53,59,.8)!important">'+ */
							                '<div class="book-card-body">'+
							                    '<div class="body-title">'+ 
													// Detail은 가져온 정보를 토대로 popup을 띄워 준다.
						                    		'<a href="#" onclick="detailView(' + ((arr_isbn.length > 1) ? arr_isbn[1] : arr_isbn[0]) +')">'+
							                    	(obj.title + ' | '+ obj.publisher) + 
							                    	'</a>'+
							                    '</div>'+
							                    '<div class="body-info">저자: ' + obj.authors + '</div>'+
							                    '<div class="body-info">번역: ' + obj.trans + '</div>'+
							                    '<div class="body-info">상태: ' + obj.status + '</div>'+
							                    '<div class="body-bookmark">'+
							                        '<button type="button" class="btn btn-circle btn-danger btn-sm">'+
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

		// 인기 검색어 관련 버튼을 클릭 했을 때
		$(".popular").click(function(){
			// 인기 검색 키워드를 가져 온다.
			ajaxHelper({url : "/popular", method: "GET", data : {}},
				function(){	// beforeSend	
				},
				function(res){	// success
					var popular = res.popular;

					$(".popular-data").html("");
					// Data가 있는지 판단 한다.
					if(popular.length > 0){
						$(".pop-no-data").hide();

						popular.forEach(function(item, idx){	
							$(".popular-data").append(
								'<a class="dropdown-item fs-12" href="#" onclick="popularSearch(\'' + item[0] + '\')">'+
									'<span>' + (idx+1) + '. ' + item[0] + '</span>'+
									'<span class="badge badge-success ml-2 popular-badge">' + item[1] + '</span>'+
								'</a>'
							);
						});
						
					}else{	// Data가 없다면 데이터 없다는 문구를 띄워 준다.
						$(".pop-no-data").show();
					}
				},
				function(err){	// error & fail
				},
				function(){	// complete
				}
			);
		});

		// 인기 검색어에서 검색어를 클릭 했을 때 해당 키워드로 검색하도록 하는 함수.
		function popularSearch(keyword) {
			$('#input-searchword').val(keyword);

			// BookSearch 호출
			$('#search_form').submit();
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