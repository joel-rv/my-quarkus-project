package com.example;

import com.example.service.WordDocumentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.io.File;
import java.io.IOException;

import java.io.File;

@Path("/word")
public class WordResource {

    @Inject
     WordDocumentService wordDocumentService;

    @GET
    @Path("/generate/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateWordDocument(@PathParam String name) {
        String inputFilePath = "/Users/jrojas/tmp/word-document-manipulator/Primera_Notificacion_de_Cobranza.docx";
        String outputFilePath = "/Users/jrojas/tmp/word-document-manipulator/Primera_Notificacion_de_Cobranza1.docx";

        try {
            wordDocumentService.modifyWordDocument(inputFilePath, outputFilePath, name);

            File file = new File(outputFilePath);
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
