<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page import="java.io.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="shortcut icon" type="image/png" href="images/favicon.png"/>

        <title>Biblioteca</title>
    </head>
    <body>

		<c:import url="navbar.jsp"></c:import>

		<header class="container mt-5">
			<div class="row">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<h1>Gerenciar Livros</h1>
				</div>
			</div>
		</header>
		<section class="container mt-5">
			<div id="info" style="display: none;" class="alert alert-primary alert-dismissible fade show mt-3" role="alert">
				<span></span>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</section>

		<section class="container mt-3">
			<div class="row">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<input type="hidden" name="id" id="id">
					<div class="form-group">
						<label for="nome">Nome</label>
						<input type="text" name="nome" class="form-control" id="nome" placeholder="Nome">
					</div>
					<div class="form-row">
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<label for="idCategoria">Categoria</label>
							<select class="form-control" id="idCategoria"></select>
						</div>
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<label for="isbn">ISBN</label>
							<input type="text" name="isbn" class="form-control" id="isbn" placeholder="ISBN">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<label for="volume">Volume</label>
							<input type="text" name="volume" class="form-control" id="volume" placeholder="Volume">
						</div>
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<label for="autor">Autor</label>
							<input type="text" name="autor" class="form-control" id="autor" placeholder="Autor">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<label for="quantidade">Quantidade em Estoque</label>
							<input type="number" min="0" name="quantidade" class="form-control" id="quantidade" placeholder="Quantidade em estoque">
						</div>
						<div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<form name="uploadForm" action="LivroController" method="POST" enctype="multipart/form-data">
								<label for="foto">Foto</label>
								<input type="file" class="form-control-file" id="foto" name="foto">
								<input type="submit" value="submit" name="submit" />
							</form>
						</div>
					</div>

					<div class="mt-3">
						<div class="row" id="listagemUnidadesLivro"></div>
					</div>

					<div class="form-group mt-5">
						<a href="livros.jsp" class="btn btn-secondary">Cancelar</a>
						<button type="button" class="btn btn-success" id="salvar">Salvar</button>
					</div>
				</div>
			</div>
		</section>

		<footer class="container mt-5">
			<hr>
			<div class="row">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<p class="text-muted text-center">Desenvolvido por Alan Possamai & Eduardo Schenato dos Santos &copy; 2018</p>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/popper.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/barcode.js"></script>
		<script src="js/fontawesome/js/all.min.js"></script>
		<script src="js/sweetalert/dist/sweetalert2.all.min.js"></script>

		<script src="scripts/utils.js"></script>
		<script src="scripts/livro.js"></script>
	</body>
</html>
