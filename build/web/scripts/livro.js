var unidadesLivro = [];

$(document).ready(function () {
	JsBarcode(".barcode").init();

	$('#salvar').click(function () {
		salvar();
    });
});

$(window).on('load', function () {
	var id = getUrlParameter('id');
	if (id > 0) {
		$('#id').val(id);
		obter(id);
	} else {
		obterCategorias();
	}
});

function obter(id) {
	$.ajax({
		url: 'LivroController',
		type: 'GET',
		data: {
			acao: 'obter',
			id: id
		}
	}).done(function (livro) {
		if (livro && livro.unidades) {
			unidadesLivro = listarUnidades(livro.unidades);
			$('#quantidade').val(livro.quantidade).attr('min', unidadesLivro.length);

			$('#id').val(livro.id);
			$('#nome').val(livro.nome);
			$('#isbn').val(livro.isbn);
			$('#volume').val(livro.volume);
			$('#autor').val(livro.autor);
			$('#foto').val(livro.foto);

			$('#idCategoria').append(
				$('<option>', { val: livro.categoria.id, text: livro.categoria.nome })
			).val(livro.categoria.id).prop('disabled', true);
		} else {
			$('#info').show().children(':first-child').html("Não foi possível obter os dados.");
		}
	});
}

function listarUnidades(unidades) {
	var unLivros = [];

	$('#listagemUnidadesLivro').append(
		$('<div>', { class: 'col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mb-3' }).append(
			$('<h2>', {	text: 'Unidades' })
		)
	);

	$.each(unidades, function () {
		unLivros.push(this.id);

		var unidade = $('<div>', { class: 'col-xl-3 col-lg-3 col-md-4 col-sm-6 col-12 mb-3' }).append(
			$('<div>', { class: 'card' }).append(
				$('<div>', { class: 'text-center' }).append(
					$('<div>', { class: 'text-center' }).append(
						$('<svg>', { class: 'barcode', 'jsbarcode-value': this.id.toString().padStart(12, '0') })
					)
				)
			)
		);

		$('#listagemUnidadesLivro').append(unidade);
	});

	$("body").html($("body").html());
	return unLivros;
}

function obterCategorias() {
	$.ajax({
		url: 'CategoriaController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (categorias) {
		listarCategorias(categorias);
	});
}

function listarCategorias(categorias) {
	$('#idCategoria').empty();

	if (categorias && Array.isArray(categorias)) {
		$.each(categorias, function () {
			$('#idCategoria').append(
				$('<option>', { val: this.id, text: this.nome })
			);
		});
	}
}

function salvar() {
	$.ajax({
		url: 'LivroController',
		type: 'POST',
		dataType: 'json',
		data: {
			'acao': 'salvar',
			'id': $('#id').val(),
			'nome': $('#nome').val(),
			'isbn': $('#isbn').val(),
			'volume': $('#volume').val(),
			'autor': $('#autor').val(),
			'quantidade': ($('#quantidade').val() || 1),
			'idCategoria': $('#idCategoria').val(),
			'unidadesLivro': unidadesLivro
		}
	}).done(function (result) {
		console.log(result);

		if (result == true) {
			window.location.href = 'livros.jsp';
		} else {
			$('#info').show().children(':first-child').html("Não foi possível salvar.");
		}
	});
}