package io.github.andrey.service.impl;

import io.github.andrey.domain.entity.Usuario;
import io.github.andrey.domain.repository.Usuarios;
import io.github.andrey.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UsuarioServiceImpl implements UserDetailsService {
   @Autowired
   private PasswordEncoder encoder;

   @Autowired
   private Usuarios usuariosRepository;

   @Transactional
   public Usuario salvar(Usuario usuario){
       return usuariosRepository.save(usuario);
   }


   public UserDetails autenticar(Usuario usuario){
       UserDetails user = loadUserByUsername((usuario.getLogin()));
       boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
       if(senhasBatem){
           return user;
       }
       throw new SenhaInvalidaException();
   }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario =  usuariosRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado na base de dados!"));

       String[] roles = usuario.isAdmin() ?
               new String[]{"ADMIN","USER"} : new String[]{"USER"};

       return User
               .builder()
               .username(usuario.getLogin())
               .password(usuario.getSenha())
               .roles()
               .build();

    }
}
