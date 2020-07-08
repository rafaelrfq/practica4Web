<#include "base.ftl">

<#macro page_body>
    <div class="container text-center">
        <br><u><h1>${title}</h1></u><hr>
        <h2 class="text-center">Datos del Producto</h2>
        <div class="card" >
            <img src="data:${tipo};base64,${foto}" class="card-img-top" alt="Imagen del Producto" width="auto" height="auto" style="height: 50%; width: 50%; display: block; margin-left: auto; margin-right: auto">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4">
                        <h4><b>Nombre:</b></h4>
                        <input type="text" value="${producto.nombre}" readonly>
                    </div>
                    <div class="col-md-4">
                        <h4><b>Precio:</b></h4>
                        <input type="text" value="${producto.precio}" readonly>
                    </div>
                    <div class="col-md-4">
                        <h4><b>Descripcion:</b></h4>
                        <textarea rows="4" cols="45">${producto.descripcion}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div><br>

    <#if usr == true>
        <form action="/api/productos/comentario/${producto.id}" method="post">
            <br><h2><b>Agregar comentario:</b></h2><br>
            <textarea name="comentario" id="comentario" cols="50" rows="4" placeholder="Escribir aqui..."></textarea>
            <button type="submit" class="btn btn-primary">Agregar Comentario</button>
        </form>
    </#if>

    <br><h1 class="text-center"><b>Comentarios</b></h1><br>
    <div class="row justify-content-center">
        <table class="table table-bordered table-hover" style="width: 80%">
            <thead class="thead-light text-center">
                <th>Usuario</th>
                <th>Fecha</th>
                <th>Comentario</th>
                <th>Accion</th>
            </thead>
            <tbody class="text-center">
                <#list comentarios as comment>
                    <tr>
                        <td>${comment.usuarioComentario}</td>
                        <td>${comment.fechaCreado}</td>
                        <td>${comment.contenido}</td>
                        <#if admin == true>
                            <td>
                                <a class="btn btn-danger" href="/api/productos/comentario/eliminar/${comment.id}">Eliminar</a>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </tbody>
        </table>

    </div>
</#macro>

<@display_page/>