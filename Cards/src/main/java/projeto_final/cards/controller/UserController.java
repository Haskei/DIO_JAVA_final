package projeto_final.cards.controller;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import projeto_final.cards.controller.dto.UserDto;
import projeto_final.cards.service.UserService;
import projeto_final.cards.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name="User Controller", description = "API Rest para usuarios")
public record UserController(UserService userService) {
    @GetMapping
    @Operation(summary = "Get todos os usuarios", description = "lista de usuarios")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "sucesso")})
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> users = userService.findAll();
        List<UserDto> usersDto = users.stream().map(UserDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(usersDto);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Ache usuario por id", description = "achar usuario especifico por id")
    @APIResponses(value = {@APIResponse(responseCode = "200", description = "sucesso"),
    @APIResponse(responseCode = "404",description = "falha")})
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDto userDto = new UserDto(user);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping
    @Operation(summary = "Criar usuario", description = "cria usuario e retorna os dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "erro nos dados")
    })
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = userService.create(userDto.toUser());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(new UserDto(user));
    }
    @PutMapping("/{id}")
    @Operation(summary = "update", description = "dar update em dados do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não achado"),
            @ApiResponse(responseCode = "422", description = "dados invalidos")})
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.update(id,userDto.toUser());
        return ResponseEntity.ok(new UserDto(user));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "deletar usuario",description = "deletar usuario usando id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deletado com sucesso"),
    @ApiResponse(responseCode = "404", description = "usuario não achado")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
