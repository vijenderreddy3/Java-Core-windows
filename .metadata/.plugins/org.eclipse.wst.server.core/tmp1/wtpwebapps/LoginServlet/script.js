function changeColor(){
	document.getElementById("Dateofbirth").style.color="#7AFE78";
}
var buttonLogin=function(){
	var loginUsername=document.getElementById("LiUsername").value;
	var loginPassword=document.getElementById("LiPassword").value;
	if(!((loginUsername === "") && (loginPassword === ""))){
		if (typeof(Storage) !== "undefined") {
			sessionStorage.setItem("username",loginUsername);
			
			/*window.open("Home.html");
			window.close(this);*/
		} else {
			alert("Login not succes");
		}
	}
}
function homeOnload(){
		
		if (typeof(Storage) !== "undefined") {
			document.getElementById("homeLabel1").innerHTML+=sessionStorage.getItem("username");
		} 
		
}