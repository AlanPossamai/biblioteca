<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
				<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-10">
					<h1>Livros cadastrados</h1>
				</div>
				<div class="col-2 mt-2">
					<a href="livro.jsp" class="btn btn-outline-primary w-100">Novo livro</a>
				</div>
			</div>
		</header>

		<section class="container mt-3">
			<div class="input-group">
				<input class="form-control border-secondary py-2" type="search" id="pesquisa" placeholder="Digite a sua pesquisa">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="button" id="pesquisar">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
		</section>

		<section class="container mt-3">
			<div id="info" style="display: none;" class="alert alert-primary alert-dismissible fade show mt-3" role="alert">
                <span></span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

			<div class="row" id="resultado">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th scope="col">ISBN</th>
								<th scope="col">Nome</th>
								<th scope="col">Autor</th>
								<th scope="col">Volume</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody id="listagemLivros"></tbody>
					</table>
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
		<script src="js/fontawesome/js/all.min.js"></script>
		<script src="js/sweetalert/dist/sweetalert2.all.min.js"></script>

		<script src="scripts/livros.js"></script>
	</body>
</html>
