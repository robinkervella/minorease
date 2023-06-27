package fr.simplon.minorease.controller;


import fr.simplon.minorease.dto.ChangePasswordForm;
import fr.simplon.minorease.dto.CreateUserForm;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.*;

@Controller
public class UsersController
{
    private DataSource         dataSource;
    private PasswordEncoder    passwordEncoder;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public UsersController(
            DataSource pDataSource, PasswordEncoder pPasswordEncoder, UserDetailsManager pUserDetailsManager)
    {
        dataSource = pDataSource;
        passwordEncoder = pPasswordEncoder;
        userDetailsManager = pUserDetailsManager;
    }

    @GetMapping("/login")
    String login()
    {
        return "login";
    }

    @GetMapping(path = "/admin/createUser")
    public String getCreateUserForm(@ModelAttribute(name = "user") CreateUserForm user, Model model)
    {
        if (user == null || !model.containsAttribute("user"))
        {
            model.addAttribute("user", new CreateUserForm());
        }
        return "/admin/createUser";
    }

    @PostMapping("/admin/createUser")
    @Transactional
    public String createUser(
            Principal principal,
            @Valid @ModelAttribute(name = "user") CreateUserForm user,
            BindingResult validation,
            Model model)
    {
        if (!user.getPassword().equals(user.getConfirmPassword()))
        {
            user.setConfirmPassword("");
            validation.addError(new FieldError("user", "confirmPassword", "Les mots de passe ne correspondent pas"));
        }
        if (userDetailsManager.userExists(user.getLogin()))
        {
            user.setLogin("");
            validation.addError(new ObjectError("user", "Cet utilisateur existe déjà"));
        }
        if (validation.hasErrors())
        {
            return "/admin/createUser";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        // Roles for new user
        Collection<? extends GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User(user.getLogin(), encodedPassword, roles);

        // Create the account in database with all its roles
        userDetailsManager.createUser(userDetails);
        if (principal != null)
        {
            UserDetails userDetails1 = userDetailsManager.loadUserByUsername(principal.getName());
            Collection<? extends GrantedAuthority> authorities = userDetails1.getAuthorities();
            for (GrantedAuthority authority : authorities)
            {
                boolean admin = authority.getAuthority().equals("ADMIN");
                if (admin)
                {
                    return "redirect:/admin/listUsers";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/inscription")
    public String subscribe(Model model)
    {
        model.addAttribute("user", new CreateUserForm());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String subscribe(
            @ModelAttribute(name = "user") @Valid CreateUserForm user, BindingResult validation, Model model)
    {
        return createUser(null, user, validation, model);
    }

    @GetMapping("/admin/listUsers")
    public String getUsers(Model model)
    {
        if (!model.containsAttribute("users"))
        {
            RowMapper<UserDetails> mapper = (rs, rowNum) -> {
                String username1 = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                return new User(username1, password, Collections.emptyList());
            };

            JdbcTemplate jdbc = new JdbcTemplate(dataSource);
            List<UserDetails> users = jdbc.query("select username,password,enabled from users", mapper);
            model.addAttribute("users", users);
        }
        return "/admin/listUsers";
    }

    @GetMapping("/admin/updateUser/{userId}")
    public String getUpdateUserForm(@PathVariable String userId, Model model)
    {
        if (!model.containsAttribute("user"))
        {
            CreateUserForm userForm = new CreateUserForm();
            userForm.setLogin(userId);
            model.addAttribute("user", userForm);
        }
        return "/admin/updateUser";
    }

    @PostMapping("/admin/updateUser/{userId}")
    @Transactional
    public String updateUser(
            @PathVariable String userId,
            @Valid @ModelAttribute(name = "user") CreateUserForm userForm,
            BindingResult validation,
            Model model)
    {
        // Recherche de l'utilisateur en BDD
        Optional<String> login = Optional.ofNullable(userForm.getLogin());
        UserDetails userDetails = login.map(l -> userDetailsManager.loadUserByUsername(l)).orElse(null);
        if (userDetails == null)
        {
            validation.addError(new FieldError("user", "username", "Utilisateur inconnu"));
        }

        // Vérification des 2 mots de passe fournis, ils doivent être égaux
        if (!validation.hasErrors() && !Objects.equals(userForm.getPassword(), userForm.getConfirmPassword()))
        {
            validation.addError(new FieldError("user", "confirmPassword", "Les mots de passe ne correspondent pas"));
        }

        if (!validation.hasErrors())
        {
            User.UserBuilder builder = User.withUserDetails(userDetails);
            builder.password(userDetails.getPassword());
            userDetailsManager.updateUser(builder.build());
            model.addAttribute("success", Boolean.TRUE);
        }
        return "/admin/updateUser";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model)
    {
        ChangePasswordForm userForm = new ChangePasswordForm();
        model.addAttribute("user", userForm);
        return "changePassword";
    }

    @PostMapping(value = "/change-password", consumes = {"application/x-www-form-urlencoded"})
    public String changePasswordMultipart(
            Principal principal,
            @ModelAttribute(name = "user") @Valid ChangePasswordForm user,
            BindingResult validation)
    {
        if (principal == null)
        {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsManager.loadUserByUsername(principal.getName());
        changePassword(userDetails, user, validation);

        if (validation.hasErrors())
        {
            return "changePassword";
        }
        return "redirect:/change-password?success";
    }

    @ApiResponse(responseCode = "200", description = "Mot de passe modifié avec succès")
    @ApiResponse(responseCode = "400", description = "Erreur de validation")
    @ApiResponse(responseCode = "404", description = "Utilisateur non fourni ou introuvable")
    @PostMapping(value = "/change-password", consumes = {"application/json", "text/json"})
    @ResponseBody
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordForm user, BindingResult validation)
    {
        if (user.getUsername() == null)
        {
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails = userDetailsManager.loadUserByUsername(user.getUsername());
        changePassword(userDetails, user, validation);

        if (validation.hasErrors())
        {
            return ResponseEntity.badRequest().body(validation);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Mise à jour du mot de passe utilisateur après validation du password actuel et du nouveau.
     *
     * @param userDetails
     * @param form
     * @param validation
     */
    private void changePassword(UserDetails userDetails, ChangePasswordForm form, BindingResult validation)
    {
        if (userDetails == null)
        {
            validation.addError(new FieldError("user", "username", "Utilisateur inconnu"));
        }
        else
        {
            String currentPassword = userDetails.getPassword();

            if (!passwordEncoder.matches(form.getOldPassword(), currentPassword))
            {
                validation.addError(new FieldError("user", "oldPassword", "Mauvais mot de passe"));
            }
            if (!form.getNewPassword().equals(form.getConfirmPassword()))
            {
                validation.addError(new FieldError("user", "confirmPassword", "Les mots de passe ne correspondent pas"));
            }
            if (!validation.hasErrors())
            {
                userDetailsManager.changePassword(form.getOldPassword(), passwordEncoder.encode(form.getNewPassword()));
            }
        }
    }
}