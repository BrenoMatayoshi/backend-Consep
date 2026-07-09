package br.com.consep.api.shared.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.consep.api.shared.enums.ProjectStatus;

@Component
public class StringToProjectStatusConverter implements Converter<String, ProjectStatus> {

    @Override
    public ProjectStatus convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }

        try {
            // Aqui é o pulo do gato: delegamos para o seu método @JsonCreator!
            // Assim, a regra de negócio fica centralizada apenas no Enum.
            return ProjectStatus.fromJson(source);
        } catch (IllegalArgumentException e) {
            // Se enviar um status que não existe, retorna null
            // (ou você pode deixar a exceção subir para retornar um Erro 400 Bad Request)
            return null;
        }
    }
}