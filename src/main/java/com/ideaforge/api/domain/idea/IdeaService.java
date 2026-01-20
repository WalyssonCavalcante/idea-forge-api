package com.ideaforge.api.domain.idea;

import com.ideaforge.api.domain.idea.dtos.IdeaRequestDto;
import com.ideaforge.api.domain.idea.dtos.IdeaResponseDto;
import com.ideaforge.api.infra.ai.AiGenerationService;
import org.springframework.stereotype.Service;

@Service
public class IdeaService {

    private final AiGenerationService aiService;

    public IdeaService(AiGenerationService aiService) {
        this.aiService = aiService;
    }

    public IdeaResponseDto generateIdea(IdeaRequestDto request) {
        System.out.println("[IdeaService] Orchestrating idea generation...");

        String prompt = buildPrompt(request);

        String aiContent = aiService.generateContent(prompt);

        return new IdeaResponseDto(aiContent);
    }

    private String buildPrompt(IdeaRequestDto request) {
        return """
            Role: Senior Software Architect.
            Task: Draft a technical project specification.
            
            Input Data:
            - Experience Level: %s
            - Tech Stack: %s
            - Focus Areas: %s
            
            STRICT RESPONSE RULES:
            1. Language: Brazilian Portuguese (PT-BR).
            2. Tone: Formal, corporate, and strictly technical.
            3. NO emojis or icons of any kind.
            4. NO conversational fillers (e.g., "Here is the project", "I suggest").
            5. Start immediately with the Project Title.
            
            REQUIRED MARKDOWN OUTPUT FORMAT:
            # [Project Name]
            
            ## Contexto e Objetivo
            [Brief, formal description of the business problem and solution]
            
            ## Stack Tecnológica
            [Comma separated list]
            
            ## Escopo do MVP
            - [Feature 1]
            - [Feature 2]
            - [Feature 3]
            
            ## Requisitos Não-Funcionais e Desafios (Nível %s)
            - [Architectural challenge 1]
            - [Architectural challenge 2]
            """.formatted(
                request.experienceLevel(),
                String.join(", ", request.stacks()),
                request.focusAreas() != null ? String.join(", ", request.focusAreas()) : "General",
                request.experienceLevel()
        );
    }
}
