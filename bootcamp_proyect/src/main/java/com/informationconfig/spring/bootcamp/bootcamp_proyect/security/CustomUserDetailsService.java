package com.informationconfig.spring.bootcamp.bootcamp_proyect.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private InternRepository internRepository;
    @Autowired
    private AcademyTutorRepository academyTutorRepository;
    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("========== AUTENTICACIÓN INICIADA ==========");
        System.out.println("Buscando usuario con email: " + email);
        logger.info("Buscando usuario con email: {}", email);
        Intern intern = internRepository.findByEmail(email);
        if (intern != null) {
            logger.info("Usuario encontrado en InternRepository: {}", intern.getEmail());
            return new org.springframework.security.core.userdetails.User(
                intern.getEmail(),
                intern.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_INTERN"))
            );
        }
        AcademyTutor academyTutor = academyTutorRepository.findByEmail(email);
        if (academyTutor != null) {
            logger.info("Usuario encontrado en AcademyTutorRepository: {}", academyTutor.getEmail());
            return new org.springframework.security.core.userdetails.User(
                academyTutor.getEmail(),
                academyTutor.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ACADEMY_TUTOR"))
            );
        }
        CompanyTutor companyTutor = companyTutorRepository.findByEmail(email);
        if (companyTutor != null) {
            logger.info("Usuario encontrado en CompanyTutorRepository: {}", companyTutor.getEmail());
            return new org.springframework.security.core.userdetails.User(
                companyTutor.getEmail(),
                companyTutor.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMPANY_TUTOR"))
            );
        }
        logger.warn("Usuario no encontrado con email: {}", email);
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
