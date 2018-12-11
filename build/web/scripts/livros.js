$(document).ready(function () {
	listar();

	$('#pesquisar').click(function() {
		var pesquisa = $('#pesquisa').val();
		listar(pesquisa);
	});
});

function listar(pesquisa) {
	var acao = (pesquisa && pesquisa !== '' ? 'pesquisar' : 'listar');

	$.ajax({
        url: 'LivroController',
        type: 'GET',
        data: {
            acao: acao,
			pesquisa: pesquisa
        }
    }).done(function (livros) {
		renderTable(livros);
    });
}

function renderTable(livros) {
	$('#info').hide();
	$('#listagemLivros').empty();

	if ($.isArray(livros) && livros.length > 0) {
		$.each(livros, function () {
			$('#resultado').show();
			$('#listagemLivros').append(
				$('<tr>').append(
					$('<td>', { text: this.isbn }),
					$('<td>', { text: this.nome }),
					$('<td>', { text: this.autor }),
					$('<td>', { text: this.volume }),
					$('<td>').append(
						$('<div>', { class: 'btn-group btn-group-sm' }).append(
							$('<a>', { href: 'livro.jsp?id=' + this.id, class: 'btn btn-primary', text: 'Editar ' }),
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
        title: "Você têm certeza que deseja excluir esse livro?",
        text: "Nenhum dado poderá ser recuperado, incluindo o histórico de locações.",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.ajax({
				url: 'LivroController',
				type: 'POST',
				data: {
					acao: 'excluir',
					id: id
				}
			}).done(function (response) {

				if (response == true) {
					swal('Sucesso', 'Livro excluído com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir o livro... Contate o administrador.');
				}

				listar();
			});
		}
	});
}
