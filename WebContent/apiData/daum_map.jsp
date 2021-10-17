<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>마커에 인포윈도우 표시하기</title>

</head>

<body>
	<%
	String x_pos = request.getParameter("x");
	String y_pos = request.getParameter("y");
	%>
	<div id="map" style="width: 100%; height: 350px;"></div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=00328688bd76250c4d76e5ccc43f3ae4">
		
	</script>
	<script>
	
		var x_p = '<%=x_pos%>';
		var y_p = '<%=y_pos%>';

		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(x_p, y_p), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		var map = new kakao.maps.Map(mapContainer, mapOption);

		// 마커가 표시될 위치입니다 
		var markerPosition = new kakao.maps.LatLng(x_p, y_p);

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			position : markerPosition
		});

		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);

		var iwContent = '<div style="padding:5px;">국립중앙의료원 D동 <br><a href="https://map.kakao.com/link/map/국립중앙의료원 D동,'+x_p+','+y_p+'" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/국립중앙의료원 D동,'+x_p+','+y_p+'" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		iwPosition = new kakao.maps.LatLng(x_p, y_p); //인포윈도우 표시 위치입니다

		// 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			position : iwPosition,
			content : iwContent
		});

		// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
		infowindow.open(map, marker);
	</script>
</body>

</html>