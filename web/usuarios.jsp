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
					<h1>Cadastro de Usuários</h1>
				</div>
				<div class="col-2 mt-2">
					<a href="usuario.jsp" class="btn btn-outline-primary w-100">Nova usuário</a>
				</div>
			</div>
		</header>

		<section class="container mt-5">
			<div class="row">
				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Pesquisar..." id="pesquisa">
						<div class="input-group-append">
							<button class="btn btn-secondary" type="button" id="pesquisaUsuario">Pesquisar</button>
						</div>
					</div>
				</div>
			</div>

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
                                                            <th>ID</th>
                                                            <th>Nome</th>
                                                            <th>E-Mail</th>
                                                            <th>Ações</th>
							</tr>
						</thead>
						<tbody id="listaUsuarios"></tbody>
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
		<script src="js/sweetalert/dist/sweetalert2.all.min.js"></script>

		<script src="scripts/usuarios.js"></script>
	</body>
</html>
