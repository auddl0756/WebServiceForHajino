package com.roon.board.web;

import com.roon.board.config.auth.LoginUser;
import com.roon.board.config.auth.dto.SessionUser;
import com.roon.board.service.PostsService;
import com.roon.board.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    // [javax.servlet.http public interface HttpSession]
    //Provides a way to identify a user
    // across more than one page request or visit to a Web site
    // and to store information about that user.
    //The servlet container uses this interface to create a session between an HTTP client and an HTTP server.
    // The session persists for a specified time period,
    // across more than one connection or page request from the user.

    //홈페이지
//    @GetMapping("/")
//    public String index(Model model){
//        model.addAttribute("posts",postsService.findAllDesc());
//
//        SessionUser user =(SessionUser) httpSession.getAttribute("user");
//        if(user!=null) model.addAttribute("userName",user.getName());
//        return "index";
//    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());
        if(user!=null) model.addAttribute("userName",user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto =postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }


    // 추가 : 로그인 안 해도 글 읽기는 가능하도록 하기 위해
    // 그리고 글 수정과 글 보기를 분리하기 위해
    @GetMapping("/posts/show/{id}")
    public String postsShow(@PathVariable Long id,Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-show";
    }

}
