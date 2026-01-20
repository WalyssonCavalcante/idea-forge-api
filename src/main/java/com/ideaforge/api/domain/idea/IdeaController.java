package com.ideaforge.api.domain.idea;


import com.ideaforge.api.domain.idea.dtos.IdeaRequestDto;
import com.ideaforge.api.domain.idea.dtos.IdeaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ideas")

@CrossOrigin(origins = "http://localhost:4200")
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService){
        this.ideaService = ideaService;
    }

    @PostMapping("/generate")
    public ResponseEntity<IdeaResponseDto> generate(@RequestBody IdeaRequestDto request){
        System.out.println("Pedido recebido: " + request);

        var response = ideaService.generateIdea(request);

        return ResponseEntity.ok(response);
    }
}
