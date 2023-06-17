package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.UsersEntity;
import com.example.tiketku_finalproject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository R;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public Page<UsersEntity> getAll(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return R.findAll(pageable);
    }
    public UsersEntity getById(UUID id_user){
        return R.findById(id_user).get();
    }

    public UsersEntity updateUser(UsersEntity param) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        UsersEntity userExist =  R.findById(param.getUuid_user()).get();
        userExist.setEmail(param.getEmail());
        userExist.setPassword(passwordEncoder.encode(param.getPassword()));
        userExist.setGender(param.getGender());
        userExist.setFull_name(param.getFull_name());
        userExist.setPhone(param.getPhone());
        userExist.setModified_at(currentDateTime);
        return R.save(userExist);
    }
    public UsersEntity updateToken(UsersEntity param){
        UsersEntity userExist =  R.findByEmail(param.getEmail()).get();
        userExist.setToken(jwtService.generateToken(param.getEmail()));
        return R.save(userExist);
    }

    public UsersEntity addUsers(UsersEntity param) {
        Optional<UsersEntity> userEmailExist = R.findByEmail(param.getEmail());
        Optional<UsersEntity> userPhoneExist = R.findByPhone(param.getPhone());
        if (userEmailExist.isPresent()) {
            throw new RuntimeException("Email " + param.getEmail() + " Sudah Ada");
        }
        if(userPhoneExist.isPresent()){
            throw new RuntimeException("Nomer Telepon " + param.getPhone() + " Sudah Ada");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        param.setUuid_user(generateUUID());
        param.setCreated_at(currentDateTime);
        param.setPassword(passwordEncoder.encode(param.getPassword()));
        param.setRoles("ROLE_USER");
        return R.save(param);

    }

//    public List<UsersEntity> addMultipleUsers(List<UsersEntity> param) {
//        List<UsersEntity> list = new ArrayList<>();
//
//        for(UsersEntity user : param){
//            Optional<UsersEntity> userExsist = R.findById(user.getUid_user());
//            if(userExsist.isPresent()){
//                throw new RuntimeException("User ID " +user.getUid_user() + " Sudah Ada");
//            }
//            else{
//                user.setPassword(passwordEncoder.encode(user.getPassword()));
//                list.add(R.save(user));
//            }
//        }
//        return list;
//    }


    public UsersEntity delUser(UUID param){
        UsersEntity delete = R.findById(param).get();
        R.deleteById(param);
        return delete;
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    public UsersEntity resetPassword(String email, String newPassword) {
        UsersEntity user = R.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User dengan username " + email + " tidak ditemukan"));
        UsersEntity userExist =  R.findByEmail(email).get();
        userExist.setToken(jwtService.generateToken(userExist.getEmail()));
        R.save(userExist);

        // Memeriksa apakah password baru sama dengan password sebelumnya
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("Password baru tidak boleh sama dengan password sebelumnya");
        }

//        // Memeriksa apakah password sebelumnya sesuai
//        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
//            throw new RuntimeException("Password sebelumnya tidak sesuai");
//        }

        // Mengubah password baru dan menyimpan perubahan
        user.setPassword(passwordEncoder.encode(newPassword));
        return R.save(user);
    }
}
