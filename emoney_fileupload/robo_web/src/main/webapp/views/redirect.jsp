<!DOCTYPE html >
<html>
<head>
<title>대한민국 명품투자의 멘토, X1</title>
<script type="text/javascript">
var msg = "${msg}";
if( msg != "" ) {
	alert(msg);
}
var url = "${url}";
if( url == "reload" ) {
	opener.location.reload();
	window.close();
} else if ( url == "" ) {
	//window.close();
	top.window.opener = top;
	top.window.open('','_parent', '');
	top.window.close();
} else {
	location.href = url;	
}
</script>
</head>
<body>
</body>
</html>