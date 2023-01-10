<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<link href="/resources/css/test.css" rel="stylesheet">
</head>
<body>
<!-- https://codepen.io/hey-fk/pen/xxgXgxM?editors=1100 -->
<svg aria-hidden="true" style="position: absolute; width: 0; height: 0; overflow: hidden;" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
	  <defs>
	    <symbol id="icon-star" viewBox="0 0 26 28">
	      <path d="M26 10.109c0 0.281-0.203 0.547-0.406 0.75l-5.672 5.531 1.344 7.812c0.016 0.109 0.016 0.203 0.016 0.313 0 0.406-0.187 0.781-0.641 0.781-0.219 0-0.438-0.078-0.625-0.187l-7.016-3.687-7.016 3.687c-0.203 0.109-0.406 0.187-0.625 0.187-0.453 0-0.656-0.375-0.656-0.781 0-0.109 0.016-0.203 0.031-0.313l1.344-7.812-5.688-5.531c-0.187-0.203-0.391-0.469-0.391-0.75 0-0.469 0.484-0.656 0.875-0.719l7.844-1.141 3.516-7.109c0.141-0.297 0.406-0.641 0.766-0.641s0.625 0.344 0.766 0.641l3.516 7.109 7.844 1.141c0.375 0.063 0.875 0.25 0.875 0.719z"></path>
	    </symbol>
	    <symbol id="icon-star-half" viewBox="0 0 13 28">
	      <path d="M13 0.5v20.922l-7.016 3.687c-0.203 0.109-0.406 0.187-0.625 0.187-0.453 0-0.656-0.375-0.656-0.781 0-0.109 0.016-0.203 0.031-0.313l1.344-7.812-5.688-5.531c-0.187-0.203-0.391-0.469-0.391-0.75 0-0.469 0.484-0.656 0.875-0.719l7.844-1.141 3.516-7.109c0.141-0.297 0.406-0.641 0.766-0.641v0z"></path>
	    </symbol>
	  </defs>
	</svg>
<section class="starRating" data-value="0" data-num="1">
	
	<div class="comment-stars">
	
	  <input class="comment-stars-input" type="radio" name="rating" value="5" id="rating-5">
	  <label class="comment-stars-view" for="rating-5"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="4.5" id="rating-4.5" > <label class="comment-stars-view is-half" for="rating-4.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="4" id="rating-4"> <label class="comment-stars-view" for="rating-4"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="3.5" id="rating-3.5"> <label class="comment-stars-view is-half" for="rating-3.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="3" id="rating-3"> <label class="comment-stars-view" for="rating-3"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="2.5" id="rating-2.5"> <label class="comment-stars-view is-half" for="rating-2.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="2" id="rating-2"> <label class="comment-stars-view" for="rating-2"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="1.5" id="rating-1.5"> <label class="comment-stars-view is-half" for="rating-1.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="1" id="rating-1"> <label class="comment-stars-view" for="rating-1"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating" value="0.5" id="rating-0.5"> <label class="comment-stars-view is-half" for="rating-0.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	</div>
</section>
	
<section class="starRating ex" data-value="0" data-num="2">
	<!-- tip) input radio는 name으로 한 세트를 구분함 -->
	<!-- tip) input id 와 label for을 일치 시켜서 input radio의 "클릭" 범위를 label까지 확장시킬수있음 -->
	<div class="comment-stars">
	
	  <input class="comment-stars-input" type="radio" name="rating1" value="5" id="2rating-5">
	  <label class="comment-stars-view" for="2rating-5"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="4.5" id="2rating-4.5" > <label class="comment-stars-view is-half" for="2rating-4.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="4" id="2rating-4"> <label class="comment-stars-view" for="2rating-4"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="3.5" id="2rating-3.5"> <label class="comment-stars-view is-half" for="2rating-3.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="3" id="2rating-3"> <label class="comment-stars-view" for="2rating-3"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="2.5" id="2rating-2.5"> <label class="comment-stars-view is-half" for="2rating-2.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="2" id="2rating-2"> <label class="comment-stars-view" for="2rating-2"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="1.5" id="2rating-1.5"> <label class="comment-stars-view is-half" for="2rating-1.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="1" id="2rating-1"> <label class="comment-stars-view" for="2rating-1"><svg class="icon icon-star">
	      <use xlink:href="#icon-star"></use>
	    </svg></label>
	  <input class="comment-stars-input" type="radio" name="rating1" value="0.5" id="2rating-0.5"> <label class="comment-stars-view is-half" for="2rating-0.5"><svg class="icon icon-star-half">
	      <use xlink:href="#icon-star-half"></use>
	    </svg></label>
	</div>
</section>






<script type="text/javascript">
$(document).ready(function(){
	console.log($("div.a").html());
	// 
	// $('.comment-stars-input[value='+currentValue+']').prop('checked', true);
	
	/* $(".comment-stars-input").attr("id", $(".comment-stars-input").closest("section.starRating").data("num") + "-"+ $(".comment-stars-input").attr("id"));
	$(".comment-stars-view").attr("for", $(".comment-stars-input").closest("section.starRating").data("num") + "-"+ $(".comment-stars-view").attr("for"));
	console.log($(".comment-stars-input").attr("id"));
	console.log($(".comment-stars-view").attr("for")); */
	
 	  $(".comment-stars-view").on({
	    mouseenter: function () {
	    	//alert("a");
		//	console.log($(this));
		//	console.log($(this).closest("section.starRating"));
			$(this).closest("section.starRating").addClass("active");
	    },
	    mouseleave: function () {
	    	$(this).closest("section.starRating").removeClass("active");
	    }
	
	
		
	});
	
	
/* 별점 선택 */
	/* $(document).on("click", ".comment-stars-view", function(event){
		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
			// -> 이벤트 위임 필요
	//	var currentValue = 3;
		console.log($(this).prev('.comment-stars-input'));
		console.log("before: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + "/ #"+$(this).prev('.comment-stars-input').prop("id")+" checked: "+$(this).prev('.comment-stars-input').prop('checked'));
	

		if($(this).prev('.comment-stars-input').val() == $(this).closest("section.starRating").data("value")){ // 별점 해제
			
			$(this).prev('.comment-stars-input').prop('checked', false);
			
			$(this).closest("section.starRating").data("value", 0);
			
		} else{
			
			$(this).siblings('.comment-stars-input').prop('checked', false); 

			$(this).prev('.comment-stars-input').prop('checked', true); 
			
			$(this).closest("section.starRating").data("value", $(this).prev('.comment-stars-input').val());
			

		}

		console.log("after: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + "/ #"+$(this).prev('.comment-stars-input').prop("id")+" checked: "+$(this).prev('.comment-stars-input').prop('checked'));

	}); */
 
	 $(".comment-stars-input").off("click").on("click", function(event){
		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
			// -> 이벤트 위임 필요
	//	var currentValue = 3;
	//	console.log("a");
			console.log($(this));
		
		console.log("before: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + "/ # checked: "+$(this).prop('checked'));
	

		if($(this).val() == $(this).closest("section.starRating").data("value")){ // 별점 해제
			
			$(this).prop('checked', false);
			
			$(this).closest("section.starRating").data("value", 0);
			
		} else{
			
			$(this).siblings('.comment-stars-input').prop('checked', false); 

			$(this).prop('checked', true); 
			
			$(this).closest("section.starRating").data("value", $(this).val());
			

		}

		console.log("after: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + " checked: "+$(this).prop('checked'));
		console.log($("#rating-5").prop('checked'));

	}); 
	
	/* console.log("data-value: " + $("#rating-5").closest("section.starRating").data("value") +"/ num: "+$("#rating-5").closest("section.starRating").data("num") + "/ #"+$("#rating-5").prop("id")+" checked: "+$("#rating-5").prop('checked'));
	$(document).on("click", ".comment-stars-input", function(event){
		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
			// -> 이벤트 위임 필요
	//	var currentValue = 3;
		var TargetInput = event.target
		console.log("a");
		console.log("aa: " +event.target.id);
		console.log("aa: " +event.currentTarget.id);
		console.log("data-value: " + $("#rating-5").closest("section.starRating").data("value") +"/ num: "+$("#rating-5").closest("section.starRating").data("num") + "/ #"+$("#rating-5").prop("id")+" checked: "+$("#rating-5").prop('checked'));
		console.log("before: data-value: " + $(event.target).closest("section.starRating").data("value") +"/ num: "+$(event.target).closest("section.starRating").data("num") + "/ #"+$(this).prop("id")+" checked: "+$(event.target).prop('checked'));
	

		if($(event.target).val() == $(event.target).closest("section.starRating").data("value")){ // 별점 해제
			
			$(event.target).prop('checked', false);
			
			$(event.Target).closest("section.starRating").data("value", 0);
			
		} else{
			
			$(event.target).siblings('.comment-stars-input:checked').prop('checked', false); 

		//	$(this).prop('checked', true); 
			
			$(event.target).closest("section.starRating").data("value", $(event.target).val());
			

		}

		console.log("after: data-value: " + $(event.target).closest("section.starRating").data("value") +"/ num: "+$(event.target).closest("section.starRating").data("num") + "/ #"+$(event.target).prop("id")+" checked: "+$(event.target).prop('checked'));


	}); */
	
 	/*  $(document).on("click", ".comment-stars-view", function(event){
 		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
 		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
 			// -> 이벤트 위임 필요
 	//	var currentValue = 3;
 			var target = event.target;
 		console.log(target.previousElementSibling);
 		console.log("before: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + "/ #"+$(this).prev('.comment-stars-input').prop("id")+" checked: "+$(this).prev('.comment-stars-input').prop('checked'));
 	

 		if($(this).prev('.comment-stars-input').val() == $(this).closest("section.starRating").data("value")){ // 별점 해제
 			
 			$(this).prev('.comment-stars-input').prop('checked', false);
 			
 			$(this).closest("section.starRating").data("value", 0);
 			
 		} else{
 			
 			$(this).siblings('.comment-stars-input').prop('checked', false); 

 			$(this).prev('.comment-stars-input').prop('checked', true); 
 			
 			$(this).closest("section.starRating").data("value", $(this).prev('.comment-stars-input').val());
 			

 		}

 		console.log("after: data-value: " + $(this).closest("section.starRating").data("value") +"/ num: "+$(this).closest("section.starRating").data("num") + "/ #"+$(this).prev('.comment-stars-input').prop("id")+" checked: "+$(this).prev('.comment-stars-input').prop('checked'));


 	}); */
});

</script>

</body>
</html>