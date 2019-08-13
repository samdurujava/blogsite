//package com.example.blogproject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    BlogRepository blogRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("ADMIN"));
//
//        Role adminRole = roleRepository.findByRole("ADMIN");
//        Role userRole = roleRepository.findByRole("USER");
//
//
//        User admin = new User("study.javaclass@gmail.com",
//                userService.encode("password"),
//                "Sam",
//                "Duru",
//                true,
//                "admin");
//        userService.saveAdmin(admin);
//
//        Blog blog = new Blog("Mother's Day",
//                "Happy mother day to the most loving mom in the world",
//                LocalDateTime.of(2019, 05, 15, 14, 15),
//                "https://res.cloudinary.com/mhussainshah1/image/upload/v1551323218/java-bootcamp/roohi_bano.jpg",
//                admin);
//        blogRepository.save(blog);
//
//        blog = new Blog("Today is holiday",
//                "Dave wants to give holiday because we did good in class",
//                LocalDateTime.now(),
//                "https://res.cloudinary.com/mhussainshah1/image/upload/v1551473204/java-bootcamp/completed.png",
//                admin);
//        blogRepository.save(blog);
//
//        blog = new Blog("Independence Day",
//                "I am proud to be an American where at least i am free. " +
//                        "I wont forget men who die gave that right to me",
//                LocalDateTime.of(2019, 07, 04, 10, 11),
//                "https://res.cloudinary.com/mhussainshah1/image/upload/v1563037288/java-bootcamp/american-flag-internal-halyard.jpg",
//                admin);
//        blogRepository.save(blog);
//
//        blog = new Blog("Valentines Day",
//                "I am still looking for someone to come in my life",
//                LocalDateTime.of(2019, 02, 14, 05, 06),
//                "https://res.cloudinary.com/mhussainshah1/image/upload/v1551323276/java-bootcamp/hebapsmsapt323cll6ak.jpg",
//                admin);
//        blogRepository.save(blog);
//    }
//}
