var min,sec;
 
function Minutes(data) {
  for (var i = 0; i < data.length; i++)
    if (data.substring(i, i + 1) == ":")
      break;
 return (data.substring(0, i));
}
 
function Seconds(data) {
  for (var i = 0; i < data.length; i++)
    if (data.substring(i, i + 1) == ":")
      break;
  return (data.substring(i + 1, data.length));
}
 
function Down() {
  sec--;
  if (sec == -1) {
    sec = 59; min--;
  }
 
  if(min==3 && sec == 0){
    var d = new Date();
    var hours = d.getHours();
    var minutes = d.getMinutes();
   if(minutes<1){
     minutes = "0" + minutes;
   }
   alert("Please save your work in next 1 minutes to avoid any Data Loss due to Session timeout.");
 }
 if (min == 0 && sec == 0) {
   window.status = "Your session has timed out."
   document.location.href="Login";
  }else {
    down = setTimeout("Down()", 1000);
  }
}
 
function timeIt() {
  min = 1 * Minutes("5:00");
  sec = 0 + Seconds("5:00");
  Down();
}


