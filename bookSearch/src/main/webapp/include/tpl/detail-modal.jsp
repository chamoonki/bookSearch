<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Modal -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalLabel">Book Detailed Lookup</h5>
        <div class="ribbon" onclick="actionBookmark()">
           <i class="fa fa-star"></i>
        </div>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body bookmark">
        <div class="media">
        	<div>
        		<div class="book-img mb-1"></div>
        	</div>
        	<div class="media-body">
				<p>
					Title : <b><span class="book-title"></span></b>
				</p>
				<p>
					Authors : <span class="book-authors"></span>
				</p>
				<p>
					Translators : <span class="book-translators"></span>
				</p>
				<p>
					Publisher : <span class="book-publisher"></span>
				</p>
				<p>
					ISBN : <span class="book-isbn"></span>
				</p>
				<p>
					Barcode : <span class="book-barcode"></span>
				</p>
				<p>
					Selling Price ( Normal Price) : <span class="book-price"></span>
				</p>
				<p>
					Status : <span class="book-status"></span>
				</p>
				<h5 class="mt-0">book introduction</h5>
				<p class="book-contents"></p>
			</div>
        </div>
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


<script type="text/javascript">
	var chekBookmark = false;
	
	// Detail View를 보여주기 위한 함수
	function detailView(isbn){
		ajaxHelper(
			// ajax info
			{url:"/detailView", method: "GET", data: {isbn: isbn}},
			function(){	// beforeSend
				$("#detailModal").modal();
			},
			function(res){	// success
				
				var jsonBook = JSON.parse(res.book);

				// 가져온 데이터가 있는 지 확인 한다.
				if(jsonBook.meta.total_count > 0){
					// 북마크 설정이 되어 있다면 Ribbon Div 를 설정 해 준다.
					eval('$(".fa-star")' + (res.bookmark ? '.show()' : '.hide()') + '');

					// Book 정보를 변수에 넣어 준다.
					var detail = jsonBook.documents[0];

					// html에 세팅 해 준다.
					$(".book-img").html('<img class="mr-3 img-thumbnail" width="100" src="'+(detail.thumbnail ? detail.thumbnail : './assets/img/default-book.svg')+'" alt="책 표지 ">');
					$('.book-title').text(detail.title|| '-');
					$('.book-authors').text(detail.authors|| '-');
					$(".book-translators").text(detail.trans || '-');
					$(".book-publisher").text(detail.publisher|| '-');
					$(".book-isbn").text(detail.isbn|| '-');	
					$(".book-barcode").text(detail.barcoed|| '-');
					$(".book-price").text(
							detail.sale_price + "원 ( " + detail.price
									+ "원 )");
					$(".book-status").text(detail.status|| '-');
					$(".book-contents").text(detail.contents|| '-');
				}

				

				

			},	
			function(){	// error & fail
			},
			function(){	// complete
				
			}
		);	
	}

	function actionBookmark(){
		chekBookmark = !chekBookmark;
		eval('$(".fa-star")' + (chekBookmark ? '.show()' : '.hide()') + '');
		console.log('#!#!#!#!#!# ');
	}
</script>