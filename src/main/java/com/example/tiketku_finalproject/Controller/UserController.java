package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.UsersEntity;
import com.example.tiketku_finalproject.Response.*;
import com.example.tiketku_finalproject.Service.JwtService;
import com.example.tiketku_finalproject.Service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value ="/Users")
@Api(value = "Users")
public class UserController {
    @Autowired
    UsersService us;
    @Autowired
    CommonResponseGenerator urg;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/Login")
    public CommonResponse<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if(authentication.isAuthenticated()){
                UsersEntity getUser = new UsersEntity();
                getUser.setEmail(authRequest.getEmail());
                getUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                getUser.setToken(jwtService.generateToken(authRequest.getEmail()));

                UsersEntity user = us.updateToken(getUser);
                log.info(String.valueOf(user.getEmail()), "Sukses Login menggunakan email : " + user.getEmail() + " dan password : " + user.getPassword());
                return urg.succsesResponse(user, "Sukses Login");
            }
            else {
                throw new UsernameNotFoundException("Invalid user resquest !");
            }
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }
    }

    @PostMapping("/Register")
    @Operation(description = "Menambahkan User Tertentu Dari Database")
    public CommonResponse<UsersEntity> addUsers(@RequestBody RegisterRequest param) {
        try {
            UsersEntity regUser = new UsersEntity();
            regUser.setEmail(param.getEmail());
            regUser.setPassword(param.getPassword());
            regUser.setFull_name(param.getFull_name());
            regUser.setGender(param.getGender());
            regUser.setPhone(param.getPhone());
            regUser.setRoles(param.getRoles());
            regUser.setToken(jwtService.generateToken(param.getEmail()));
            UsersEntity user = us.addUsers(regUser);
            log.info(String.valueOf(user), "Sukses Menambahkan Data " + user.getUuid_user());
            return urg.succsesResponse(user, "Sukses Menambahkan Data");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }
    }

    @PutMapping("/ResetPassword")
    @Operation(description = "User Melakukan Reset Password")
    public CommonResponse<UsersEntity> validatePassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        try {
            UsersEntity userReset = new UsersEntity();
            userReset.setEmail(resetPasswordRequest.getEmail());
            userReset.setPassword(resetPasswordRequest.getNew_password());
            userReset.setToken(jwtService.generateToken(resetPasswordRequest.getEmail()));
            UsersEntity user = us.resetPassword(userReset.getEmail(),userReset.getPassword());
            log.info(String.valueOf(user), "Password Berhasil Diganti " + user.getUuid_user());
            return urg.succsesResponse(user, "Paswword Berhasil Diganti");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }
    }


    @GetMapping()
    @Operation(description = "Menampilkan Semua Users")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<ResponseEntity<List<UsersEntity>>> getAll(
            @RequestParam(defaultValue = "0")int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        try {
            Page<UsersEntity> userResult = us.getAll(pageNumber, pageSize);
            List<UsersEntity> userData = userResult.getContent();
//        int currentPage = userResult.getNumber();
//        int totalPages = userResult.getTotalPages();
            long totalItems = userResult.getTotalElements();

            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.add("X-Total-Count", String.valueOf(totalItems));
            log.info("Sukses Tampil Data");
            return urg.succsesResponse(ResponseEntity.ok().headers(userHeaders).body(userData),"Sukses Tampil Data");
        }
        catch (Exception e){
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }
    }

    @GetMapping(value = "/findUser/{id_user}") //yang ada di dalam {} disamakan dengan
    @Operation(description = "Mencari User Berdasarkan ID User")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<UsersEntity> getById(@PathVariable UUID id_user){ // yang ini "id_user"
        try {
            UsersEntity user = us.getById(id_user);
            log.info(String.valueOf(user),"Sukses Mencari Data " + user.getUuid_user());
            return urg.succsesResponse(user,"Sukses Mencari Data " + user.getUuid_user());
        }
        catch (Exception e){
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }

    }

    @PutMapping(value = "/updateUser")
    @Operation(description = "Mengupdate Users Tertentu Dari Database")
    //@PreAuthorize("hasAuthority('ROLE_USERS')")
    public CommonResponse<UsersEntity> updateUser(@RequestBody UsersEntity param){

        try {
            UsersEntity user = us.updateUser(param);
            log.info(String.valueOf(user),"Sukses Update Data " +user.getUuid_user());
            return urg.succsesResponse(user,"Sukses Update Data " +user.getUuid_user());
        }
        catch (Exception e){
            log.warn(String.valueOf(e));
            return urg.failedResponse(e.getMessage());
        }

    }

    @DeleteMapping(value = "/deleteUser/{id_user}")
    @Operation(description = "Menghapus Users Tertentu Dari Database Berdasarkan ID User")
    //@PreAuthorize("hasAuthority('ROLE_USERS')")
    public CommonResponse<UsersEntity> deleteUser(@PathVariable UUID id_user){
        try {
            UsersEntity user = us.delUser(id_user);
            log.info("Sukses Menghapus Data " + user.getUuid_user());
            return urg.succsesResponse(user, "Sukses Menghapus Data " + user.getUuid_user());
        }
        catch (EmptyResultDataAccessException e) {
            log.warn(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
        catch (Exception e) {
            log.error(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete User", e);
        }
    }
}
