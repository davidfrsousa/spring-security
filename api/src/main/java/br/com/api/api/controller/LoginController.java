package br.com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.api.model.user.AuthenticationDTO;
import br.com.api.api.model.user.LoginResponseDTO;
import br.com.api.api.model.user.RegisterDTO;
import br.com.api.api.model.user.User;
import br.com.api.api.repository.UserRepository;
import br.com.api.api.security.TokenService;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationDTO loginRequest) {
        /*
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.login(), loginRequest.password());
        Authentication authenticationResponse =
                 this.authenticationManager.authenticate(authenticationRequest);
        System.out.println(authenticationRequest);
        var token = tokenService.generateToken((User)authenticationResponse.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));*/
        
        var authenticationRequest = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        System.out.println("authentication request: "+authenticationRequest);
        //User user = new User(loginRequest.login(), loginRequest.password());
        //System.out.println(authenticationRequest);
        try{
            var authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
            System.out.println("authentication response: "+authenticationResponse);
            var token = tokenService.generateToken((User)authenticationResponse.getPrincipal());
            System.out.println(token);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch(BadCredentialsException ex){
            System.out.println("Incorrect username or password"+ex);
        }
        
        //System.out.println("authentication response: "+authenticationResponse);

       //var token = tokenService.generateToken((User)authenticationResponse);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerRequest) {
        //System.out.println("_______________"+registerRequest+"fim ");
        if(this.userRepository.findByLogin(registerRequest.login())!= null) return ResponseEntity.badRequest().build();
        String encryptedPw = new BCryptPasswordEncoder().encode(registerRequest.password());

        User user = new User(registerRequest.login(),encryptedPw,registerRequest.role());

        this.userRepository.save(user);
        
        return ResponseEntity.ok().build();
    }

}
