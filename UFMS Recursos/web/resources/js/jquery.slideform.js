$(document).ready(function() {
    $('.oculto').hide();
    
    $('button#btn-novo').click(function(){
        $('button#btn-novo').hide();
        $(".oculto").slideDown();
    });
 
    $('button#btn-fechar').click(function() {
        $('button#btn-novo').show();
        $(".oculto").slideUp();
    });
    
    $('a.btn-editar').click(function(){
        $('button#btn-novo').hide();
        $(".oculto").slideDown();
    });
});