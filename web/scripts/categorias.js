$(document).ready(function() {
	listar();

    $("#pesquisaCategoria").click(function() {
        $.ajax({
            url: 'CategoriaController',
            type: 'GET',
            data: {
				acao: 'pesquisar',
                pesquisa: $('#pesquisa').val()
            }
        }).done(function (categorias) {
			renderTable(categorias);
        });
    });
});

function listar() {
	$.ajax({
		url: 'CategoriaController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (categorias) {
		renderTable(categorias);
	});
}

function renderTable(categorias) {
	$('#info').hide();
	$('#listaCategorias').empty();

	if ($.isArray(categorias) && categorias.length > 0) {
		$('#resultado').show();

		$.each(categorias, function () {
			$('#listaCategorias').append(
				$('<tr>').append(
					$('<td>', { text: this.id }),
					$('<td>', { text: this.nome }),
					$('<td>').append(
						$('<div>', { class: 'btn-group btn-group-sm' }).append(
							$('<a>', { href: 'categoria.jsp?id=' + this.id, class: 'btn btn-primary', text: 'Editar ' }),
							$('<button>', { type: 'button', type: 'button', class: 'btn btn-danger', text: 'Excluir ', onClick: 'excluir(' + this.id + ')' })
						)
					)
				)
			);
		});
	} else {
		$('#resultado').hide();
		$('#info').show().children(':first-child').html("Nenhum resultado foi retornado");
	}
}

function excluir(id) {
    swal({
        title: "Você têm certeza que deseja excluir a categoria?",
        text: "Nenhum dado poderá ser recuperado, incluindo o histórico de locações.",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.ajax({
				url: 'CategoriaController',
				type: 'POST',
				data: {
					acao: 'excluir',
					id: id
				}
			}).done(function (response) {

				if (response == true) {
					swal('Sucesso', 'Categoria excluída com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir a categoria... Contate o administrador.');
				}

				listar();
			});
		}
	});
}