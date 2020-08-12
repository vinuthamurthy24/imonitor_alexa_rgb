<%-- Copyright Â© 2012 iMonitor Solutions India Private Limited --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.IMonitorProperties"%>
<%@page import="in.xpeditions.jawlin.imonitor.util.Constants"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%><html>

<div style="color: blue;display:none;"><p class="message"><s:property value="#session.message" /></p>
<%ActionContext.getContext().getSession().remove("message"); %>
</div>

<script>
$("#expiryTime").datepicker({
	showOn: 'button', 
	buttonImage: '/imonitor/resources/images/calendar.gif', 
	buttonImageOnly: true,
	beforeShow: function(input, inst) {
		$(".ui-datepicker").css('z-index', 5000);
	},
	  minDate: 0,
	onClose: function(dateText, inst) { 
		$(".ui-datepicker").css('z-index', 1);
	},
	dateFormat:'<%= IMonitorProperties.getPropertyMap().get(Constants.DATE_FORMAT_SERVER) %>'
	});
var handleHourSelectChange = function(){
	
	var sHour = $("#HourSelect").val();
	var sMinute = $("#MinuteSelect").val();
	$("#expireTime").val(sHour+","+sMinute);
	
};

var handleMinuteSelectChange = function(){
	
	var sHour = $("#HourSelect").val();
	var sMinute = $("#MinuteSelect").val();
	$("#expireTime").val(sHour+","+sMinute);
};

$(document).ready(function() {
$("#HourSelect").bind("change", handleHourSelectChange);
$("#MinuteSelect").bind("change", handleMinuteSelectChange);

});
	</script>
	<s:form action="toSaveTempPassword" method="post" id="saveCustomerReportForm" cssClass="ajaxinlinepopupform">
	<s:hidden name="expiretime" id="expireTime" value="0,0"></s:hidden>
	<table>
	
	<tr>
	<s:password name="tempPassword" label="Password" cssClass="required crosscheck confirmpass editdisplayStar required" commonclass="confirmpass"></s:password>
	</tr>
	<tr>
	<s:password label="Confirm Password" cssClass="required crosscheck confirmpass editdisplayStar required" commonclass="confirmpass"></s:password>
	</tr>
	<tr>
	<td><s:textfield name="expiredate" type="text" label="Select expiry date" cssClass="datetime required" id='expiryTime' readonly='true'></s:textfield></td>
	</tr>
		<tr>
			<td>
				Select hour:
				<select id="HourSelect">
				    <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				</select>
				
				</td>
				<td>
				Select minute:
				<select id="MinuteSelect">
				    <option value="0">00</option>
				    <option value="1">01</option>
				    <option value="2">02</option>
				    <option value="3">03</option>
				    <option value="4">04</option>
				    <option value="5">05</option>
				    <option value="6">06</option>
				    <option value="7">07</option>
				    <option value="8">08</option>
				    <option value="9">09</option>
				    <option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
				    <option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				    <option value="24">24</option>
				    <option value="25">25</option>
				    <option value="26">26</option>
				    <option value="27">27</option>
				    <option value="28">28</option>
				    <option value="29">29</option>
				    <option value="30">30</option>
				    <option value="31">31</option>
				    <option value="32">32</option>
				    <option value="33">33</option>
				    <option value="34">34</option>
				    <option value="35">35</option>
				    <option value="36">36</option>
				    <option value="37">37</option>
				    <option value="38">38</option>
				    <option value="39">39</option>
				    <option value="40">40</option>
				    <option value="41">41</option>
				    <option value="42">42</option>
				    <option value="43">43</option>
				    <option value="44">44</option>
				    <option value="45">45</option>
				    <option value="46">46</option>
				    <option value="47">47</option>
				    <option value="48">48</option>
				    <option value="49">49</option>
				    <option value="50">50</option>
				    <option value="51">51</option>
				    <option value="52">52</option>
				    <option value="53">53</option>
				    <option value="54">54</option>
				    <option value="55">55</option>
				    <option value="56">56</option>
				    <option value="57">57</option>
				    <option value="58">58</option>
				    <option value="59">59</option>
				</select>
			</td>
		</tr>
		
	
	</table>
	 <div id="alertSection" style='margin: 28px 0px 0px 0px;'>
	<!-- <input type='checkbox' id='sms'/> --><s:textfield name="smsToSend" type="text" label='SMS' id='smstosend' ></s:textfield>
	<br></br>
	<!-- <input type='checkbox' id='Email'/> --><s:textfield name="emailToSend" type="text" label='E-Mail' id='emailtosend' ></s:textfield>
	</div> 
	<s:submit value="Save" ></s:submit>
	</s:form>
	</html>