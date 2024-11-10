package org.Shiv.API.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

    @Data
    @Getter
    @Setter
    public class SchemaGenerationResponse {
        private List<Candidate> candidates;
    }

