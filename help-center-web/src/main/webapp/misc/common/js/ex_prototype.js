/**
 * 各类原型扩展
 * @author daipeng
 */
var hasPrototype = true;
/**
 *	String原型扩展
 */

String.prototype.trim = function(){ 
	return this.replace(/(^\s+)|(\s+$)|(^　+)|(　+$)/g, "");
};

String.prototype.ltrim = function(){
    return this.replace(/(^\s+)|(^　+)/g,"");
};

String.prototype.rtrim = function(){
    return this.replace(/(\s+$)|(　+$)/g,"");
};

String.prototype.isNumber = function() {
	return (this.isInt() || this.isFloat() || this.isPercent());
};

String.prototype.isFloat = function() {
	return /^(?:-?|\+?)\d+(,\d\d\d)*\.\d+$/g.test(this);
};

String.prototype.isInt = function() {
	return /^(?:-?|\+?)\d+(,\d\d\d)*$/g.test(this);
};

String.prototype.isPercent = function() {	
	return /^(?:-?|\+?)\d+(,\d\d\d)*(\.\d+)?\%$/g.test(this);
};

String.prototype.isPlus = function() {
	return this.isPlusInt() || this.isPlusFloat();
};

String.prototype.isPlusFloat = function() {
	return /^\+?\d*\.\d+$/g.test(this);
};

String.prototype.isPlusInt = function() {
	return /^\+?\d+$/.test(this);
};

String.prototype.isMinus = function() {
	return this.isMinusInt() || this.isMinusFloat();
};

String.prototype.isMinusFloat = function() {
	return /^-\d*\.\d+$/g.test(this);
};

String.prototype.isMinusInt = function() {
	return /^-\d+$/g.test(this);
};

String.prototype.isLeastCharSet = function() {
	return !(/[^A-Za-z0-9_]/g.test(this));
};

String.prototype.isEmail = function() {
	return /^\w+@.+\.\w+$/g.test(this);
};

String.prototype.isZip = function() {
	return /^\d{6}$/g.test(this);
};

String.prototype.isPhone = function() {
	return /^(\d{2,4}-)?((\(\d{3,5}\))|(\d{3,5}-))?\d{3,18}(-\d+)?$/g.test(this);
};

String.prototype.isMobile = function() {
	return /^((13)|(15))\d{9}$/g.test(this);
};

String.prototype.isTel = function() {
	return this.isMobileTelephone() || this.isFixedTelephone();
};

String.prototype.hasMark = function(_mark) {
	var reg = new RegExp("("+_mark+")","g");
	return ((this.match(reg))?(this.match(reg)).length:0)>0;
};

String.prototype.getLen = function() {
	var cArr = this.match(/[^\x00-\xff]/ig);  
    return this.length + (cArr==null?0:cArr.length); 	
};


String.prototype.sub = function(n) {
    var reg = /[^\x00-\xff]/g,start = Math.floor(n/2);
    if(this.replace(reg, "**").length <= n){
        return [this];
    }
    for(var i=start,len = this.length; i<len; i++) {
        if(this.substr(0, i).replace(reg, "**").length>=n){
            return [this.substr(0, i),this.substr(i,this.length)];
        }
    }
    return this;
};

/**
 * Date原型扩展
 * Date.prototype.isLeapYear 判断闰年
 * Date.prototype.Format 日期格式化
 * Date.prototype.DateAdd 日期计算
 * Date.prototype.DateDiff 比较日期差
 * Date.prototype.toString 日期转字符串
 * Date.prototype.toArray 日期分割为数组
 * Date.prototype.DatePart 取日期的部分信息
 * Date.prototype.MaxDayOfDate 取日期所在月的最大天数
 * Date.prototype.WeekNumOfYear 判断日期所在年的第几周
 * stringToDate 字符串转日期型
 * nextDay
 * prevDay
 * nextDays
 * prevDays
 * dayOfCurWeek
 * daysOfCurWeek
 * dayOfNextWeek
 * daysOfNextWeek
 * dayOfPrevWeek
 * daysOfPrevWeek
 */

/**
 * 获取当前日期下_i天
 * @param {Object} _i
 */ 
Date.prototype.nextDay = function(_i){	
    var day = this;   
    day.setDate(day.getDate()+_i);   
    return day; 
};

/**
 * 获取当前日期以后的n天
 */
Date.prototype.nextDays = function(_n){	
    var days = new Array();   
    for(var i=1;i<=_n;i++) {       
        days[i-1] = this.nextDay(i);   
    }   
    return days;  
};

/**
 * 指定日期的前_i天
 * @param {Object} _i
 */
Date.prototype.prevDay = function(_i){
    var day = this;
    var preDay = new Date(this.getTime());
    if(!!_i||_i==0){
        preDay.setDate(day.getDate()-_i);
    }else{
	    preDay.setDate(day.getDate()-1);
    }
    return preDay; 
};

/**
 * 取得当前日期的前_n天
 */	
Date.prototype.prevDays = function(_n, _pattern){
	var days = new Array();   
    for(var i=_n;i>=1;i--) {        
        days[_n-i] = _pattern?this.prevDay(i).Format(_pattern):this.prevDay(i);   
    }   
    return days;
};

/**
 * get the date range of previous 3 days 
 */
Date.prototype.prevDay3 = function(){
	return [this.prevDay(3).Format("yyyy-MM-dd"),this.prevDay(1).Format("yyyy-MM-dd")];
};

/**
 * get the date range of previous 7 days 
 */
Date.prototype.prevDay7 = function(){
	return [this.prevDay(7).Format("yyyy-MM-dd"),this.prevDay(1).Format("yyyy-MM-dd")];
};

/**
 * get the date range of previous 15 days 
 */
Date.prototype.prevDay15 = function(){
	return [this.prevDay(15).Format("yyyy-MM-dd"),this.prevDay(1).Format("yyyy-MM-dd")];
};

/**
 * get the date range of previous 30 days 
 */
Date.prototype.prevDay30 = function(){
	return [this.prevDay(30).Format("yyyy-MM-dd"),this.prevDay(1).Format("yyyy-MM-dd")];
};

/**
 * get the date range of previous 90 days 
 */
Date.prototype.prevDay90 = function(){
	return [this.prevDay(90).Format("yyyy-MM-dd"),this.prevDay(1).Format("yyyy-MM-dd")];
};

/**
 * 获取当前日期一周内的一天
 * @param {Object} _i
 */
Date.prototype.dayOfCurWeek = function(_i){
	var myDate = this;	
	var start = new Date(this.getTime());	
	/*
	 * 取得一周内的第一天、第二天、第三天... (本月的第几天-本周的第几天+下标)  
	 * myDate.getDay()==0?7:myDate.getDay() 本周表示周一~周日，和标准有区别
	 */
    start.setDate(myDate.getDate() - (myDate.getDay()==0?7:myDate.getDay()) + _i);
    return start;   
};

/**
 * 获取当前日期一周内的7天
 */
Date.prototype.daysOfCurWeek = function(){
	var days = new Array();   
    for(var i=1;i<=7;i++) {   
        days[i-1] = this.dayOfCurWeek(i);   
    }   
    return days;  
};

/**
 * 获取当前日期下一周
 * @param {Object} _i
 */ 
Date.prototype.dayOfNextWeek = function(_i){	
    var day = this;   
    day.setDate(day.nextDay(7).getDate());	
    return day.dayOfCurWeek(_i); 
};

/**
 * 获取当前日期下一周的7天
 */
Date.prototype.daysOfNextWeek = function(){	
    var days = new Array();   
    for(var i=1;i<=7;i++) {       
        days[i-1] = this.dayOfNextWeek(i);
    }   
    return days;  
};

/**
 * 指定日期的上一周(前七天)
 * @param {Object} _i
 */
Date.prototype.dayOfPrevWeek = function(_i){
	var day = this;	
    day.setDate(day.prevDay(7).getDate());	
    return day.dayOfCurWeek(_i); 
};

/**
 * 取得上一周的日期数(共七天)
 */	
Date.prototype.daysOfPrevWeek = function(){
	var days = new Array();   
    for(var i=1;i<=7;i--) {        
        days[i-1] = this.dayOfPrevWeek(i);   
    }   
    return days;
};

/**
 * 获取本月的第几天
 * @param {Object} _i
 */
Date.prototype.dayOfCurMonth = function(_i){
	return new Date(this.getFullYear(), this.getMonth(), _i+1);
};
/**
 * 获取月的第一天
 */
Date.prototype.firstDayOfMonth = function(){
	return new Date(this.getFullYear(), this.getMonth(), 1);
};
/**
 * 获取月的最后一天
 */
Date.prototype.lastDayOfMonth = function(){
	var date = new Date(this.getFullYear(),this.getMonth()+1,1);	
	return new Date(date.getTime()-864e5);
};
/**
 * 获取昨天的开始和结束日期
 */
Date.prototype.yesterday = function(){	
	return [(this.prevDay(1)).Format("yyyy-MM-dd"),(this.prevDay(1)).Format("yyyy-MM-dd")];
};
/**
 * 获取周的第一天
 */
Date.prototype.firstDayOfWeek = function(){
	var date = new Date(this.getTime());	
	return date.dayOfCurWeek(1);
};
/**
 * 获取周的最后一天
 */
Date.prototype.lastDayOfWeek = function(){
	var date = new Date(this.getTime());	
	return date.dayOfCurWeek(7);
};
/**
 * 获取本周的开始和结束日期
 * @param _prevDayNum  最大日期配置近N天，如2-近2天，默认近1天
 * @returns {Array}
 */
Date.prototype.curWeek = function(_prevDayNum){
	var day = new Date(this.getTime()),today = new Date(),prevDayNum = !!_prevDayNum?_prevDayNum:1;
	if(day.getFullYear()==today.getFullYear()&&day.getWeekNumber()==today.getWeekNumber()){
		var endDay = today.prevDay();
        if(today.getDay()>Number(prevDayNum)){
            endDay = today.prevDay(prevDayNum);
        }else if(today.getDay()==Number(prevDayNum)){
            endDay = today;
        }
		return [(today.dayOfCurWeek(1)).Format("yyyy-MM-dd"),endDay.Format("yyyy-MM-dd")];
	}else{
		return [day.firstDayOfWeek().Format("yyyy-MM-dd"),day.lastDayOfWeek().Format("yyyy-MM-dd")];
	}	
};

/**
 * 获取上周的开始和结束日期 
 */
Date.prototype.preWeek = function(){
	var date = new Date(this.getTime());	
    var day = new Date(date.prevDay(7).getTime());    
    return [(day.dayOfCurWeek(1)).Format("yyyy-MM-dd"),(day.dayOfCurWeek(7)).Format("yyyy-MM-dd")];	
};

/**
 * 获取前几周周的开始和结束日期 
 */
Date.prototype.preWeekN = function(weekDayCounts){
	var date = new Date(this.getTime());	
    var day = new Date(date.prevDay(7*weekDayCounts).getTime());    
    return [(day.dayOfCurWeek(0)).Format("yyyy-MM-dd"),(day.dayOfCurWeek(6)).Format("yyyy-MM-dd")];
};

/**
 * 获取后几周周的开始和结束日期 
 */
Date.prototype.nextWeekN = function(weekDayCounts){
	var date = new Date(this.getTime());	
    var day = new Date(date.nextDay(7).getTime());    
    return [(day.dayOfCurWeek(1)).Format("yyyy-MM-dd"),(day.dayOfCurWeek(7)).Format("yyyy-MM-dd")];	
};

/**
 * 获取本月的开始和结束日期
 * @param _prevDayNum
 * @returns {Array}
 */
Date.prototype.curMonth = function(_prevDayNum){
	var day = new Date(this.getTime()),today = new Date(),prevDayNum=!!_prevDayNum?_prevDayNum:1;
	//judge if in current month
	if(day.getFullYear()==today.getFullYear()&&day.getMonth()==today.getMonth()){
		var endDay = (today.getDate()>=(Number(prevDayNum)+1))?today.prevDay(prevDayNum):(today.prevDay().getMonth()==today.getMonth()?today.prevDay():today);
		return [(new Date(day.getFullYear(), day.getMonth(), 1)).Format("yyyy-MM-dd"),endDay.Format("yyyy-MM-dd")];
	}else{
		return [day.firstDayOfMonth().Format("yyyy-MM-dd"),day.lastDayOfMonth().Format("yyyy-MM-dd")];
	}	
};

/**
 * 获取上个月的开始和结束日期 
 */
Date.prototype.preMonth = function(_format){
	var start = new Date(this.getFullYear(), this.getMonth()-1, 1);		
	var end = new Date(this.getFullYear(), this.getMonth(), 1); 
	days = (end-start)/(1000*60*60*24);
    if(!!_format){
        return (new Date(this.getFullYear(), this.getMonth()-1, 1)).Format("yyyy-MM")
    }else{
	    return [(new Date(this.getFullYear(), this.getMonth()-1, 1)).Format("yyyy-MM-dd"),(new Date(this.getFullYear(), this.getMonth()-1, days)).Format("yyyy-MM-dd")];
    }
};

/**
 * 获取下个月的开始和结束日期 
 */
Date.prototype.nextMonth = function(_format){
	var start = new Date(this.getFullYear(), this.getMonth()+1, 1);		
	var end = new Date(this.getFullYear(), this.getMonth()+2, 1); 
	days = (end-start)/(1000*60*60*24);
    if(!!_format){
        return (new Date(this.getFullYear(), this.getMonth()+1, 1)).Format("yyyy-MM")
    }else{
	    return [(new Date(this.getFullYear(), this.getMonth()+1, 1)).Format("yyyy-MM-dd"),(new Date(this.getFullYear(), this.getMonth()+1, days)).Format("yyyy-MM-dd")];
    }
};
/**
 * 获取最近几个月
 * @param _i
 * @returns {Array}
 */
Date.prototype.prevMonth = function(_i){
    var date = new Date(this.getTime()),curYear = date.getFullYear(),curMonth = date.getMonth();
    if(curMonth-_i<=0){
        curYear--;
        curMonth+=12;
    }
    return [new Date(curYear, curMonth-(_i-1), 1).Format("yyyy-MM-dd"), new Date(this.getTime()).prevDay().Format("yyyy-MM-dd")];
}
/**
 * 获取指定月的最近3个月
 * @returns {*}
 */
Date.prototype.prevMonth3 = function(){
    return new Date(this.getTime()).prevMonth(3);
}
/**
 * 获取指定月的最近6个月
 * @returns {*}
 */
Date.prototype.prevMonth6 = function(){
    return new Date(this.getTime()).prevMonth(6);
}
/**
 * 获取指定月的最近12个月
 * @returns {*}
 */
Date.prototype.prevMonth12 = function(){
    return new Date(this.getTime()).prevMonth(12);
}
/**
 * 获取最近几个月的计数
 * @param _i
 * @returns 2013.3（2013年8月15日近3个月）
 */
Date.prototype.prevMonthCounter = function(_i){
    var date = new Date(this.getTime()),curYear = date.getFullYear(),curMonth = date.getMonth();
    return curYear + "." +_i;
}
/**
 * 获取指定月的最近3个月
 * @returns {*}
 */
Date.prototype.prevMonthCounter3 = function(){
    return new Date(this.getTime()).prevMonthCounter(3);
}
/**
 * 获取指定月的最近6个月
 * @returns {*}
 */
Date.prototype.prevMonthCounter6 = function(){
    return new Date(this.getTime()).prevMonthCounter(6);
}
/**
 * 获取指定月的最近6个月
 * @returns {*}
 */
Date.prototype.prevMonthCounter12 = function(){
    return new Date(this.getTime()).prevMonthCounter(12);
}
/**
 * 获取上季度范围从始月到末月
 * @returns {*}
 */
Date.prototype.prevSeason = function(){
    var date = new Date(this.getTime()),curYear = date.getFullYear(),curMonth = date.getMonth(),step = 3;
    if(curMonth-step<=0){
        curYear--;
    }
    var range = {};
    for(var i=0;i<12;i++){
        if(i-step<0){
            range[i] = [new Date(curYear, 9, 1).Format("yyyy-MM-dd"), new Date(curYear, 11, 1).lastDayOfMonth().Format("yyyy-MM-dd")];
        }else{
            range[i] = [new Date(curYear, Math.floor(i/step-1)*step, 1).Format("yyyy-MM-dd"), new Date(curYear, Math.floor(i/step-1)*step+2, 1).lastDayOfMonth().Format("yyyy-MM-dd")];
        }
    };
    return range[curMonth];
}
/**
 * 获取上季度的序列
 * @returns {*}  2013.4(2013年第4季度）
 */
Date.prototype.prevSeasonCounter = function(){
    var date = new Date(this.getTime()),curYear = date.getFullYear(),curMonth = date.getMonth(),step = 3,counter = 0;
    if(curMonth-step<=0){
        curYear--;
        counter = curYear+"."+4;
    }else{
        counter = curYear+"."+Math.floor(curMonth/step);
    }
    return counter;
}

/**
 * 获取当月的所有日期
 * @param {Object} _i
 */
Date.prototype.daysOfCurMonth = function(_i){
	var days = new Array();	
	if(_i){		
		for(var i=0;i<_i;i++){			
			days[i] = new Date(this.getFullYear(), this.getMonth(), i+1);
		}		
	}else{		
		var start = new Date(this.getFullYear(), this.getMonth(), 1);		
		var end = new Date(this.getFullYear(), this.getMonth()+1, 1); 
		days = (end-start)/(1000*60*60*24);			
	}
	return days;	
};

/**
 * 判断闰年
 */ 
Date.prototype.isLeapYear = function(){    
    return (0==this.getFullYear()%4&&((this.getFullYear()%100!=0)||(this.getFullYear()%400==0)));    
};    
   
/**
 * 日期格式化   
 *  格式 YYYY/yyyy/YY/yy 表示年份   
 *  MM/M 月份   
 *  W/w 星期   
 *  dd/DD/d/D 日期   
 *  hh/HH/h/H 时间   
 *  mm/m 分钟   
 *  ss/SS/s/S 秒   
 * @param {Object} formatStr
 */
Date.prototype.Format = function(_formatStr){    
    var str = _formatStr;
    var Week = ['日','一','二','三','四','五','六'];   
   
    str=str.replace(/yyyy|YYYY/,this.getFullYear());    
    str=str.replace(/yy|YY/,(this.getFullYear() % 100)>9?(this.getFullYear() % 100).toString():'0' + (this.getFullYear() % 100));    
   
    str=str.replace(/MM/,(this.getMonth()+1)>9?(this.getMonth()+1).toString():'0'+(this.getMonth()+1));    
    str=str.replace(/M/g,this.getMonth()+1);    
   
    str=str.replace(/w|W/g,Week[this.getDay()]);    
   
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());    
    str=str.replace(/d|D/g,this.getDate());    
   
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());    
    str=str.replace(/h|H/g,this.getHours());    
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());    
    str=str.replace(/m/g,this.getMinutes());    
   
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());    
    str=str.replace(/s|S/g,this.getSeconds());    
   
    return str;    
};  
   
/**
 * 日期计算   
 * @param {Object} strInterval
 * @param {Object} Number
 */  
Date.prototype.DateAdd = function(strInterval, Number) {    
    var dtTmp = this;   
    switch (strInterval) {    
        case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));   
        case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));   
        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));   
        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));   
        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));   
        case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());   
        case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());   
        case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());   
    }   
};   
   
/**
 * 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
 * @param {Object} strInterval
 * @param {Object} dtEnd
 */  
Date.prototype.DateDiff = function(strInterval, dtEnd) {    
    var dtStart = this,dval = 0;    
    switch (strInterval) {
        case 's' :dval = parseInt((dtEnd - dtStart) / 1000); 
        		  break;
        case 'n' :dval = parseInt((dtEnd - dtStart) / 60000);   
        		  break;
        case 'h' :dval = parseInt((dtEnd - dtStart) / 3600000);   
        		  break;
        case 'd' :dval = parseInt((dtEnd - dtStart) / 86400000)+1;  
        		  break;
        case 'w' :dval = parseInt((dtEnd - dtStart) / (86400000 * 7)); 
        		  break;
        case 'm' :dval = (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);   
        		  break;
        case 'y' :dval = dtEnd.getFullYear() - dtStart.getFullYear(); 
        		  break;
    } 
    return dval;
};   
   
/**
 * 日期输出字符串，重载了系统的toString方法
 * @param {Object} showWeek
 */
/*Date.prototype.toString = function(showWeek){    
    var myDate= this;   
    var str = myDate.toLocaleDateString();   
    if (showWeek){    
        var Week = ['日','一','二','三','四','五','六'];   
        str += ' 星期' + Week[myDate.getDay()];   
    }   
    return str;   
}; */
   
/**
 * 把日期分割成数组   
 */ 
Date.prototype.toArray = function(){    
    var myDate = this;   
    var myArray = Array();   
    myArray[0] = myDate.getFullYear();   
    myArray[1] = myDate.getMonth();   
    myArray[2] = myDate.getDate();   
    myArray[3] = myDate.getHours();   
    myArray[4] = myDate.getMinutes();   
    myArray[5] = myDate.getSeconds();   
    return myArray;   
};   
   
/**
 * 取得日期数据信息   
 * 参数 interval 表示数据类型   
 * y 年 m月 d日 w星期 ww周 h时 n分 s秒   
 * @param {Object} interval
 */  
Date.prototype.DatePart = function(interval){    
    var myDate = this;   
    var partStr = '';   
    var Week = ['日','一','二','三','四','五','六'];   
    switch (interval){    
        case 'y' :partStr = myDate.getFullYear()+"";break;   
        case 'm' :partStr = (myDate.getMonth()+1)+"";break;   
        case 'd' :partStr = myDate.getDate()+"";break;   
        case 'w' :partStr = Week[myDate.getDay()]+"";break;   
        case 'ww' :partStr = myDate.WeekNumOfYear()+"";break;   
        case 'h' :partStr = myDate.getHours()+"";break;   
        case 'n' :partStr = myDate.getMinutes()+"";break;   
        case 's' :partStr = myDate.getSeconds()+"";break;   
    }   
    return partStr;   
};   
   
/**
 * 取得当前日期所在月的最大天数
 */  
Date.prototype.MaxDayOfDate = function()   
{    
    var myDate = this;   
    var ary = myDate.toArray();   
    var date1 = (new Date(ary[0],ary[1]+1,1));   
    var date2 = date1.dateAdd(1,'m',1);   
    var result = dateDiff(date1.Format('yyyy-MM-dd'),date2.Format('yyyy-MM-dd'));   
    return result;   
};   
   
/**
 * 取得当前日期所在周是一年中的第几周
 */  
Date.prototype.WeekNumOfYear = function(){    
    var myDate = this;   
    var ary = myDate.toArray();   
    var year = ary[0];   
    var month = ary[1]+1;   
    var day = ary[2];   
    document.write("<script language='VBScript'\> \n");   
    document.write("myDate = DateValue("+month+"-"+day +"-"+year+") \n");   
    document.write("result = DatePart('ww', myDate) \n");   
    document.write(" \n");   
    return result;   
};  

/**
 * Array扩展
 * 禁用。造成for-in循环时数组读取错误
 * 校验方法移至Tool原型中
 */
//Array.prototype.contains = function(item){
//  return RegExp("\\b"+item+"\\b").test(this);
//};
//
//Array.prototype.max = function(){
//    return Math.max.apply({}, this);
//};





