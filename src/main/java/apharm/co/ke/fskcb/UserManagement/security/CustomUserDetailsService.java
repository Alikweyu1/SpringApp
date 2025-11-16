package apharm.co.ke.fskcb.UserManagement.security;

import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserProfileEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import apharm.co.ke.fskcb.UserManagement.datasource.entities.repositories.IUserAccountsRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserAccountsRepository IUserAccountsRepository; //repository for user

    public CustomUserDetailsService(IUserAccountsRepository IUserAccountsRepository) {

        this.IUserAccountsRepository = IUserAccountsRepository;
    }

    //implementation of UserDetailsService
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String staffNo) throws UsernameNotFoundException {
        UserAccountEntity userAccount = IUserAccountsRepository.findByStaffNo(staffNo).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email : " + staffNo));
        return new User(userAccount.getStaffNo(), userAccount.getPassword(), getAuthorities(userAccount.getRoles()));
    }


//    // map roles to authorities
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Set<apharm.co.ke.fskcb.UserManagement.datasource.entities.UserRoleEntity> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (UserRoleEntity userRole : roles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getName()));
            for (UserProfileEntity userProfile : userRole.getUserProfiles()) {
                authorities.add(new SimpleGrantedAuthority(userProfile.getName()));
            }
        }
        return authorities;
    }
}



