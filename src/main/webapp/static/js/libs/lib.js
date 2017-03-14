$(document).ready(function() {


	loadTags();



});

var atualizaTags = function(){
	$( '#progress-bar-tags' ).show();
	$( '#progress-bar-tags-titulo' ).show();
	$.ajax({
		url : "atualizaTags",
		type : "GET",
		contentType : "application/json; charset=utf-8",
		cache : false,
		processData : false,
		success : function(data, textStatus, jQxhr) {
			window.location.reload();
			
		},
		error : function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);

		}
	});
};
var loadTags = function(){




	
	$.getJSON('getTags', function(data) {
		
		$('#jstree').jstree({ 'core' : {
			'data' : formataArrayTags(data)
		} });
	    
	 });

};
$('.tree-toggle').click(function () {
	$(this).parent().children('ul.tree').toggle(200);
});

$('#jstree').on('changed.jstree', function (e, data) {
	  var i, j, r = [];
	  var regex = /[^0-9]/;
	  for(i = 0, j = data.selected.length; i < j; i++) {
	    r.push(data.instance.get_node(data.selected[i]).text);
	  }
	  $( "#tabela-tags table tbody tr" ).remove();

	  $.getJSON('getTags', function(data) {
	         
		  data.forEach(function(obj){
	
			  r.forEach(function(value){
	
				  if(value == obj.id){
						
							$( "#tabela-tags table tbody" ).append( 
									"<tr>" +
									"     <td>"+obj.id+"</td>"+
									"     <td>"+obj.descricao+"</td>"+
									"     <td>"+obj.caminhos+"</td>"+
									"     <td>"+obj.categorias+"</td>"+
									"     <td>"+obj.autor+"</td>"+
									"     <td>"+obj.data+"</td>"+
									"</tr>");
							  					
				  }
			  });
		  
		  });
	  });
});

var formataArrayTags = function(value){
	var categoriasArray = [];
	var data = [];
	value.forEach(function(obj,indice){
		
		for(var i = 0; i < obj.categorias.length; i++){
			if(categoriasArray.length == 0){

				categoriasArray.push(obj.categorias[i]);
				data.push({ "id" : obj.categorias[i], "parent" : "#", "text" : obj.categorias[i] });
				data.push({ "id" : obj.id+indice+i, "parent" : obj.categorias[i], "text" : obj.id });
			}else if(categoriasArray.indexOf(obj.categorias[i]) == -1){
					categoriasArray.push(obj.categorias[i]);
					data.push({ "id" : obj.categorias[i], "parent" : "#", "text" : obj.categorias[i] });
					data.push({ "id" : obj.id+indice+i, "parent" : obj.categorias[i], "text" : obj.id });
				
			}else{
				data.push({ "id" : obj.id+indice+i, "parent" : obj.categorias[i], "text" : obj.id });
			}

		}

	});
	return data;
};

