<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <title>Biblioteca</title>
    </head>
    <body>
		<c:import url="navbar.jsp"></c:import>

        <header class="container mt-5">
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                    <h1>Cadastro de Usuários</h1>
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
                <form class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                    <input type="hidden" name="id" id="id">
                    <div class="form-row">
                        <div class="form-group col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <label for="nome">Nome</label>
                            <input type="text" name="nome" class="form-control" id="nome" placeholder="Nome">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                            <label for="email">E-Mail</label>
                            <input type="email" name="email" class="form-control" id="email" placeholder="E-Mail">
                        </div>
                        <div class="form-group col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                            <label for="senha">Senha</label>
                            <input type="password" name="senha" class="form-control" id="senha" placeholder="Senha">
                        </div>
                    </div>
                    <div class="form-group">
                        <a href="usuarios.jsp" class="btn btn-secondary">Cancelar</a>
                        <button type="button" class="btn btn-success" id="salvar">Salvar</button>
                    </div>
                </form>
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

		<script src="scripts/utils.js"></script>
		<script src="scripts/usuario.js"></script>
    </body>
</html>
