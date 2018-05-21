/*!
 * jqModal - Minimalist Modaling with jQuery
 * (http://dev.iceburg.net/jquery/jqModal/)
 *
 * Copyright (c) 2007,2008 Brice Burgess <bhb@iceburg.net>
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 * 
 * $Version: 03/01/2009 +r14
 * @fileoverview plugin of modal base on jQuery
 * @author Brice Burgess
 * @version 03/01/2009 +r14
 * @example
 * $("#demo").jqm();
 */
/*global jQuery:true */
(function ($) {
	$.fn.jqDrag=function(h){return i(this,h,'d');};
	$.fn.jqResize=function(h){return i(this,h,'r');};
	$.jqDnR={dnr:{},e:0,
        drag: function (v) {
            if(M.k == 'd'){
            	if((M.X+v.pageX-M.pX+M.ml)<=0)  E.css({left:-M.ml});
            	else if((M.X+v.pageX-M.pX+M.ml)>=($('body').width()-M.W)) E.css({left:($('body').width()-M.W-M.ml)});
            	else 
            		E.css({left:M.X+v.pageX-M.pX});
            	if((M.Y+v.pageY-M.pY)<=0)E.css({top:0});
            	else if((M.Y+v.pageY-M.pY+M.H)>=M.dH)E.css({top:M.dH-M.H});
            	else 
            		E.css({top:M.Y+v.pageY-M.pY});
            	}
            else 
            E.css({width:Math.max(v.pageX-M.pX+M.W,0),height:Math.max(v.pageY-M.pY+M.H,0)});
            return true;},
        //stop:function(){E.css('opacity',M.o);$(document).unbind('mousemove', J.drag).unbind('mouseup', J.stop); }
		stop:function(){//E.css('opacity',M.o);//ie bug
		$(document).unbind('mousemove',J.drag).unbind('mouseup',J.stop);}
    };
	var J=$.jqDnR,M=J.dnr,E=J.e,
	i=function(e,h,k){
		return e.each(function(){h=(h)?$(h,e):e;
		 h.bind('mousedown',{e:e,k:k},function(v){
		 	var d=v.data,p={};E=d.e;
			 // attempt utilization of dimensions plugin to fix IE issues
			 if(E.css('position') != 'relative'){try{E.position(p);}catch(e){}}
			 M={
			 	X:p.left||f('left')||0,
			 	Y:p.top||f('top')||0,
			 	W:f('width')||E[0].scrollWidth||0,
			 	H:f('height')||E[0].scrollHeight||0,
			 	pX:v.pageX,
			 	pY:v.pageY,
			 	k:d.k,
			 	o:E.css('opacity'),
			 	ml:E.css('margin-left').replace('px','')*1,
			 	dH:$(window).height() || document.body.parentNode.scrollHeight
			 	};
			//E.css({opacity:0.8});//ie bug
			$(document).mousemove($.jqDnR.drag).mouseup($.jqDnR.stop);
			 return true;
		 });
		});
	},
	f=function(k){return parseInt(E.css(k))||false;};
})(jQuery);

(function($) {	
	$.fn.jqm=function(o){
	var p={
	overlay: 20,
	overlayClass: 'whiteOverlay',
	closeClass: 'jqmClose',
	trigger: '.jqModal',
	ajax: F,
	ajaxText: '',
	target: F,
	modal: true,
	//movable: false,
	movable: true,
	retrieveTop:undefined,
	toTop: F,
	onShow: F,
	onHide: F,
	onLoad: F
	};
	return this.each(function(){if(this._jqm)return H[this._jqm].c=$.extend({},H[this._jqm].c,o);s++;this._jqm=s;
	H[s]={c:$.extend(p,$.jqm.params,o),a:F,w:$(this).addClass('jqmID'+s),s:s};
	if(p.trigger)$(this).jqmAddTrigger(p.trigger);
	});};
	
	$.fn.jqmAddClose=function(e){return hs(this,e,'jqmHide');};
	$.fn.jqmAddTrigger=function(e){return hs(this,e,'jqmShow');};
	$.fn.jqmShow=function(t){return this.each(function(){
		t=t||window.event;	
		$.jqm.open(this._jqm,t);
		});};
	$.fn.jqmHide=function(t){return this.each(function(){t=t||window.event;$.jqm.close(this._jqm,t)});};
	
	$.jqm = {
	hash:{},
	open:function(s,t){var h=H[s],c=h.c,cc='.'+c.closeClass,z=(parseInt(h.w.css('z-index'))),z=(z>0)?z:3000,o=$('<div></div>').css({height:'100%',width:'100%',position:'fixed',left:0,top:0,'z-index':z-1,opacity:c.overlay/100});if(h.a)return F;h.t=t;h.a=true;h.w.css('z-index',z);
	 if(c.modal) {if(!A[0])L('bind');A.push(s);}
	 else if(c.overlay >0){ h.w.jqmAddClose(o);/*background does not has close event;modal:true;*/}
	 else o=F;
	
	 h.o=(o)?o.addClass(c.overlayClass).prependTo('body'):F;
	 if(ie6){$('html,body').css({height:'100%',width:'100%'});if(o){o=o.css({position:'absolute'})[0];for(var y in {Top:1,Left:1})o.style.setExpression(y.toLowerCase(),"(_=(document.documentElement.scroll"+y+" || document.body.scroll"+y+"))+'px'");}}
	
	 if(c.ajax) {var r=c.target||h.w,u=c.ajax,r=(typeof r == 'string')?$(r,h.w):$(r),u=(u.substr(0,1) == '@')?$(t).attr(u.substring(1)):u;
	  r.html(c.ajaxText).load(u,function(){if(c.onLoad)c.onLoad.call(this,h);if(cc)h.w.jqmAddClose($(cc,h.w));e(h);});}
	 else if(cc)h.w.jqmAddClose($(cc,h.w));
	
	 if(c.toTop&&h.o)h.w.before('<span id="jqmP'+h.w[0]._jqm+'"></span>').insertAfter(h.o);	
	 (c.onShow)?c.onShow(h):h.w.show();e(h);
	 /*autowidth modal*/
	 _top = c.retrieveTop?c.retrieveTop(h.w):0;//retrieve the top
	 h.w.css({'margin-left':-h.w.width()/2,'width':h.w.width(),'left':$(document).width()*0.5});
	  if (c.retrieveTop) {
			h.w.css({
				top: _top
			})
		} else {
			h.w.css({
				'top':h.w.offset().top - $(document).scrollTop()
			})
		}
	 /*add movement to modal*/
	 if(c.movable&&!h.w.find('.move').length)h.w.jqDrag('.tl').find('.tl').addClass('move');
	 return F;
	},
	close:function(s){var h=H[s];if(!h.a)return F;h.a=F;
	 if(A[0]){A.pop();if(!A[0])L('unbind');}
	 if(h.c.toTop&&h.o)$('#jqmP'+h.w[0]._jqm).after(h.w).remove();
	 if(h.c.onHide)h.c.onHide(h);else{h.w.hide();if(h.o)h.o.remove();} return F;
	},
	params:{}};
	var s=0,H=$.jqm.hash,A=[],/*ie6=$.browser.msie&&($.browser.version == "6.0")&&!window.XMLHttpRequest*/ie6=$.browser&&($.browser.version == "6.0"),F=false,
	i=$('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0}),
	e=function(h){if(ie6)if(h.o)h.o.html('<p style="width:100%;height:100%"/>').prepend(i);else if(!$('iframe.jqm',h.w)[0])h.w.prepend(i); f(h);},
	f=function(h){try{$(':input:visible',h.w)[0].focus();}catch(_){}},
	L=function(t){$()[t]("keypress",m)[t]("keydown",m)[t]("mousedown",m);},
	m=function(e){var h=H[A[A.length-1]],r=(!$(e.target).parents('.jqmID'+h.s)[0]);if(r)f(h);return !r;},
	hs=function(w,t,c){return w.each(function(){var s=this._jqm;$(t).each(function() {
	 if(!this[c]){this[c]=[];$(this).click(function(){for(var i in {jqmShow:1,jqmHide:1})for(var s in this[i])if(H[this[i][s]])H[this[i][s]].w[i](this);return F;});}this[c].push(s);});});};
})(jQuery);

if (typeof (jqm) == "undefined") {
    jqm = new Object();
}else{
	}
jqm.confirm = function(o){
		
		var op={
			w:370,
			self:null,
			title:'确认',
			content:'内容',
			type:'alert',//alert ,attention,notice,question
			onConfirm:null,
			overlay: 20,
			overlayClass: 'whiteOverlay',
			retrieveTop:undefined,
			toTop: true,
			closeClass: 'jqmClose'/*,
			trigger: '.jqModal',
			ajax: F,
			ajaxText: '',
			target: F,
			modal: F,
			toTop: F,
			onShow: F,
			onHide: F,
			onLoad: F*/
		};
		op.w = o.w?o.w:op.w;
		op.self = o.self?o.self:op.self;
		op.title = o.title?o.title:op.title;
		op.content = o.content?o.content:op.content;
		op.type = o.type?o.type:op.type;
		op.onConfirm = o.onConfirm?o.onConfirm:op.onConfirm;
		op.overlay = o.overlay?o.overlay:op.overlay;
		op.overlayClass = o.overlayClass?o.overlayClass:op.overlayClass;
		op.jqmClose = o.jqmClose?o.closeClass:op.closeClass;
		op.retrieveTop = o.retrieveTop?o.retrieveTop:op.retrieveTop;
		var jqmOp={
			overlay: op.overlay,
			overlayClass: op.overlayClass,
			retrieveTop: op.retrieveTop,
			closeClass: op.closeClass/*,
			trigger: op.trigger,
			ajax: op.ajax,
			ajaxText: op.ajaxText,
			target: op.target,
			modal: op.modal,
			toTop: op.toTop,
			onShow: op.onShow,
			onHide: op.onHide,
			onLoad: op.onLoad	*/
		};
		if($('#jqmConfirm').length){
			$('#jqmConfirm').remove();	
		}
		var jqmConfirm = '<div id="jqmConfirm" style="width:'+op.w+'px;"class="modal"><h1 class="tl"><span class="tr"><span class="tit">'
						+op.title+'</span><span class="close jqmClose">×</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
						+op.type+'"><i class="ico"></i><div class="conText">'+op.content+'</div></div></div><div class="modalFooter pb20"><a href="javascript:void(0);"id="jqmConfirmBtn"class="btn btn-m mr25"><s><b><span>&#30830;&#23450;</span></b></s></a><a href="javascript:void(0);"id="jqmConfirmBtn"class="btn btn-s jqmClose"><s><b><span>&#21462;&#28040;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
		$('body').append(jqmConfirm);
		$('#jqmConfirm').jqm(jqmOp);
		$('#jqmConfirm').jqmShow();
		$('#jqmConfirmBtn').click(function(){
			if(op.onConfirm){
				//op.onConfirm();
				$('#jqmConfirm').jqmHide().remove();
				op.onConfirm.call(op.self);
			}else{
				return;	
			}
			
			});
			
}
jqm.warning = function(o){
	
	var op={
		w:370,
		self:null,
		title:'确认',
		content:'内容',
		type:'alert',//alert ,attention,notice,question
		onConfirm:null,
		overlay: 20,
		overlayClass: 'whiteOverlay',
		modal: true,
		toTop: true,
		retrieveTop:undefined/*,
		closeClass: 'jqmClose',
		trigger: '.jqModal',
		ajax: F,
		ajaxText: '',
		target: F,
		modal: F,
		toTop: F,
		onShow: F,
		onHide: F,
		onLoad: F*/
	};
	op.w = o.w?o.w:op.w;
	op.self = o.self?o.self:op.self;
	op.title = o.title?o.title:op.title;
	op.content = o.content?o.content:op.content;
	op.type = o.type?o.type:op.type;
	op.onConfirm = o.onConfirm?o.onConfirm:op.onConfirm;
	op.overlay = o.overlay?o.overlay:op.overlay;
	op.overlayClass = o.overlayClass?o.overlayClass:op.overlayClass;
	op.jqmClose = o.jqmClose?o.closeClass:op.closeClass;
	op.retrieveTop = o.retrieveTop?o.retrieveTop:op.retrieveTop;
	var jqmOp={
		overlay: op.overlay,
		overlayClass: op.overlayClass,
		modal: op.modal,
		retrieveTop: op.retrieveTop/*,
		closeClass: op.closeClass,
		trigger: op.trigger,
		ajax: op.ajax,
		ajaxText: op.ajaxText,
		target: op.target,
		modal: op.modal,
		toTop: op.toTop,
		onShow: op.onShow,
		onHide: op.onHide,
		onLoad: op.onLoad	*/
	};
	if($('#jqmConfirm').length){
		$('#jqmConfirm').remove();	
	}
	var jqmConfirm = '<div id="jqmConfirm" style="width:'+op.w+'px;"class="modal alert_modal"><h1 class="tl"><span class="tr"><span class="tit">'
					+op.title+'</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
					+op.type+'"><i class="ico"></i><div class="conText">'+op.content+'</div></div></div>'
					+'<div class="modalFooter pb20"><a href="javascript:void(0);"id="jqmConfirmBtn"class="btn btn-m mr25"><s><b><span>&#30830;&#23450;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
	$('body').append(jqmConfirm);
	$('#jqmConfirm').jqm(jqmOp);
	$('#jqmConfirm').jqmShow();
	$('#jqmConfirmBtn').click(function(){
		if(op.onConfirm){
			//op.onConfirm();
			var ret = op.onConfirm.call(op.self);
			if(ret=="undefined"||ret!=false)
				$('#jqmConfirm').jqmHide().remove();			
		}else{
			return;	
		}
		
		});
		
}
jqm.alert= function(o){
		
		var op={
			w:200,
			self:null,
			title:'确认',
			content:'内容',
			type:'success',
			onConfirm:null,
			overlay: 20,
			overlayClass: 'whiteOverlay',
			closeClass: 'jqmClose',
			retrieveTop:undefined,
			onShow: undefined,
			toTop: true,
			onHide: function(){return true;}/*,
			trigger: '.jqModal',
			ajax: F,
			ajaxText: '',
			target: F,
			modal: F,
			toTop: F,
			onShow: F,
			onHide: F,
			onLoad: F*/
		};
		op.w = o.w?o.w:op.w;
		op.self = o.self?o.self:op.self;
		op.title = o.title?o.title:op.title;
		op.content = o.content?o.content:op.content;
		op.type = o.type?o.type:op.type;
		op.onConfirm = o.onConfirm?o.onConfirm:op.onConfirm;
		op.overlay = o.overlay?o.overlay:op.overlay;
		op.overlayClass = o.overlayClass?o.overlayClass:op.overlayClass;
		op.jqmClose = o.jqmClose?o.closeClass:op.closeClass;
		op.onHide = o.onHide?o.onHide:op.onHide;
		op.retrieveTop = o.retrieveTop?o.retrieveTop:op.retrieveTop;
		var jqmOp={
			overlay: op.overlay,
			overlayClass: op.overlayClass,
			retrieveTop: op.retrieveTop,
			closeClass: op.closeClass/*,
			onHide: op.onHide,
			trigger: op.trigger,
			ajax: op.ajax,
			ajaxText: op.ajaxText,
			target: op.target,
			modal: op.modal,
			onHide: op.onHide,
			onLoad: op.onLoad	*/
		};
		if($('#jqmConfirm').length){
			$('#jqmConfirm').remove();	
		}
		var jqmAlert = '<div id="jqmAlert" style="width:'+op.w+'px;"class="modal alert_modal"><h1 class="tl"><span class="tr"><span class="tit">'
						+op.title+'</span><span class="close jqmClose">×</span></span></h1><div class="moadalCon"><div class="clearfix fakeMsg '
						+op.type+'"><i class="ico"></i><div class="conText">'+op.content+'</div></div></div><div class="modalFooter pb20"><a href="javascript:void(0);"id="jqmAlertBtn" class="btn btn-s '+jqmOp.closeClass+'"><s><b><span>&#20851;&emsp;&#38381;</span></b></s></a></div><div class="bl"><div class="br"></div></div></div>';
		$('body').append(jqmAlert);
		$('#jqmAlert').jqm(jqmOp);
		$('#jqmAlert').jqmShow();
		$('#jqmAlert .'+jqmOp.closeClass+'').click(function(){			
			$('#jqmAlert').jqmHide().remove();
			op.onHide();
		});
}
jqm.msg= function(o){
		
		var op={
			w:200,
			self:null,
			title:'确认',
			content:'内容',
			type:'success',
			onConfirm:null,
			overlay: 20,
			overlayClass: 'whiteOverlay',
			closeClass: 'jqmClose',
			retrieveTop:undefined,
			onShow: undefined,
			onHide: function(){return true;}/*,
			trigger: '.jqModal',
			ajax: F,
			ajaxText: '',
			target: F,
			modal: F,
			toTop: F,
			onShow: F,
			onHide: F,
			onLoad: F*/
		};
		op.w = o.w?o.w:op.w;
		op.self = o.self?o.self:op.self;
		op.title = o.title?o.title:op.title;
		op.content = o.content?o.content:op.content;
		op.type = o.type?o.type:op.type;
		op.onConfirm = o.onConfirm?o.onConfirm:op.onConfirm;
		op.overlay = o.overlay?o.overlay:op.overlay;
		op.overlayClass = o.overlayClass?o.overlayClass:op.overlayClass;
		op.jqmClose = o.jqmClose?o.closeClass:op.closeClass;
		op.onHide = o.onHide?o.onHide:op.onHide;
		op.retrieveTop = o.retrieveTop?o.retrieveTop:op.retrieveTop;
		var jqmOp={
			overlay: op.overlay,
			overlayClass: op.overlayClass,
			retrieveTop: op.retrieveTop,
			closeClass: op.closeClass/*,
			onHide: op.onHide,
			trigger: op.trigger,
			ajax: op.ajax,
			ajaxText: op.ajaxText,
			target: op.target,
			modal: op.modal,
			onHide: op.onHide,
			onLoad: op.onLoad	*/
		};
		if($('#jqmMsg').length){
			$('#jqmMsg').remove();	
		}
		var jqmMsg = '<div id="jqmMsg" class="modal jqMsg '+op.type+'"style="width:'+op.w+'px;"><h3 class="jqTit"><a href=""class="modalClose '
				+jqmOp.closeClass+'"></a></h3><div class="jqMsgCon clearfix"><i></i><div class="msgHtml">'+op.content+'</div></div></div>';
		$('body').append(jqmMsg);
		$('#jqmMsg').jqm(jqmOp);
		$('#jqmMsg').jqmShow();
		$('#jqmMsg .'+jqmOp.closeClass+'').click(function(){
			op.onHide();
			$('#jqmMsg').jqmHide().remove();
		});
}
jqm.loading=function(o){
		var op={
			content:'',
			size:32,
			overlay: 10,
			overlayClass: 'whiteOverlay',
			w:200
			/*
			trigger: '.jqModal',
			ajax: F,
			ajaxText: '',
			target: F,
			modal: F,
			toTop: F,
			onShow: F,
			onHide: F,
			onLoad: F*/
		};
			op.w = o.w?o.w:op.w;
			op.content = o.content?o.content:op.content;
			op.size = o.size?o.size:op.size;
			op.overlay = o.overlay?o.overlay:op.overlay;
			op.overlayClass = o.overlayClass?o.overlayClass:op.overlayClass;
		var jqmOp={
			overlay: op.overlay,
			overlayClass: op.overlayClass,
			closeClass: op.closeClass/*,
			trigger: op.trigger,
			ajax: op.ajax,
			ajaxText: op.ajaxText,
			target: op.target,
			modal: op.modal,
			toTop: op.toTop,
			onShow: op.onShow,
			onHide: op.onHide,
			onLoad: op.onLoad	*/
		};
		if($('#jqmLoading').length){
			$('#jqmLoading').remove();	
		}
		var jqmLoading = '<div id="jqmLoading" style="width:'+op.w+'px;"class="jqmLoading hide"><span class="loading loading_'+op.size+'"></span><span style="line-height:'+op.size+'px;" class="ml10 font1">'+op.content+'</span></div>';
		$('body').append(jqmLoading);
		$('#jqmLoading').jqm(jqmOp).jqDrag('.jqDrag');
		$('#jqmLoading').jqmShow();				

}