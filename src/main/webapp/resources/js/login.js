Ext.onReady(function() {
	Ext.create('Ext.Button', {
		text : '登录',
		renderTo : "button",
		handler : function() {
			Ext.Msg.alert('Status', '你点击了按钮');
		}
	});
});