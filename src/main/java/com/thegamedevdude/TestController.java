package com.thegamedevdude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping(value="/registeruser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user, ModelMap model) {
        User checkUser = DataBaseAccess.getUserFromDataBase(Persistence.createEntityManagerFactory("find"), user.getUsername());
        
        if(checkUser == null) {
            if(user.getUsername().length() < 2) {
                model.addAttribute("existing_user", "Please enter a valid username!");
                return "registerpage.jsp";
            }
            if(user.getPassword().length() < 3) {
                model.addAttribute("weak_password", "Weak password!");
                return "registerpage.jsp";
            }
            model.addAttribute("existing_user", "");
            DataBaseAccess.enterUserInDataBase(user);
            return "index.jsp";
        }else {
            model.addAttribute("existing_user", "The user already exists!");
            return "registerpage.jsp";
        }
    }

    @RequestMapping(value="/loginuser", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute User user, ModelMap model) {
        User getUser = DataBaseAccess.getUserFromDataBase(Persistence.createEntityManagerFactory("find"), user);
        if(getUser == null) {
            model.addAttribute("user_not_found", "Your username or password is incorrect!");
            return "loginpage.jsp";
        }else {
            List<Question> questions = DataBaseAccess.getListQuestions(Persistence.createEntityManagerFactory("find"));
            model.addAttribute("user_id", getUser.getUser_id());
            model.addAttribute("username", getUser.getUsername());
            model.addAttribute("listpolls", questions);
            return "account.jsp";
        }
    }

    @RequestMapping(value="/createpoll", method = RequestMethod.POST)
    public ModelAndView createPoll(HttpServletRequest request, HttpServletResponse response) {

        // Enter questions in database
        Question question = new Question();
        question.setUser_id(Long.parseLong(request.getParameter("user_id")));
        question.setQuestion(request.getParameter("question"));
        long questionId = DataBaseAccess.enterQuestionIntoDatabase(question);

        // Enter options in database
        List<String> options = new ArrayList<String>();
        for(int i = 1; i <= 4; i++) {
            String getParam = "option" + i;
            String string = request.getParameter(getParam);
            options.add(string);
        }
        DataBaseAccess.enterOptionIntoDatabase(options, questionId);

        // Print Questions on the page
        List<Question> questions = DataBaseAccess.getListQuestions(Persistence.createEntityManagerFactory("find"));
        ModelAndView model = new ModelAndView();
        model.setViewName("account.jsp");
        model.addObject("listpolls", questions);
        model.addObject("user_id", request.getParameter("user_id"));
        model.addObject("username",request.getParameter("username"));
        return model;
    }

    @RequestMapping(value="/polling", method = RequestMethod.POST)
    public ModelAndView polling(HttpServletRequest request, HttpServletResponse response) {
        Question question = DataBaseAccess.getQuestionFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("question_id")));
        List<Option> options = DataBaseAccess.getOptionsFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("question_id")));        
        List<Vote> vote = DataBaseAccess.getVoteFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("user_id")), Long.parseLong(request.getParameter("question_id")));

        ModelAndView model = new ModelAndView();
        model.setViewName("polling.jsp");
        model.addObject("user_id", request.getParameter("user_id"));
        model.addObject("question", question);
        model.addObject("options", options);
        model.addObject("username", request.getParameter("get_username"));
        
        List<Integer> forEachOptions = DataBaseAccess.getVotesForEachOptionFromDataBase(Persistence.createEntityManagerFactory("find"), options);
        model.addObject("votes", forEachOptions.get(forEachOptions.size() - 1));
        
        if(vote.size() == 0) {
            System.out.println("not voted yet");
            model.addObject("set_disabled", "");
            model.addObject("set_hidden", "hidden");
        }else {
            System.out.println(forEachOptions);
            model.addObject("progress", forEachOptions);
            model.addObject("set_disabled", "hidden");
            model.addObject("set_hidden", "");
            List<String> progressColors = options.stream()
                .map(e -> (e.getOption_id() == vote.get(0).getOption_id()) ? "bg-success": "")
                .collect(Collectors.toCollection(ArrayList::new));

            model.addObject("progress_colors", progressColors);
        }

        return model;
    }

    @RequestMapping(value="/result", method = RequestMethod.POST)
    public ModelAndView pollResult(HttpServletRequest request, HttpServletResponse response) {
        Question question = DataBaseAccess.getQuestionFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("question_id")));
        List<Option> options = DataBaseAccess.getOptionsFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("question_id")));
        List<Vote> vote = DataBaseAccess.getVoteFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("user_id")), Long.parseLong(request.getParameter("question_id")));
        
        ModelAndView model = new ModelAndView();
        model.addObject("user_id", request.getParameter("user_id"));
        model.addObject("question", question);
        model.addObject("options", options);
        model.addObject("set_disabled", "hidden");
        model.setViewName("polling.jsp");
        
        if(vote.size() == 0) {
            Vote userVote = new Vote();
            userVote.setOption_id(Long.parseLong(request.getParameter("option_id")));
            userVote.setUser_id(Long.parseLong(request.getParameter("user_id")));
            userVote.setQuestion_id(Long.parseLong(request.getParameter("question_id")));
            DataBaseAccess.enterVotesIntoDataBase(userVote);
        }

        List<Integer> eachForForOptions = DataBaseAccess.getVotesForEachOptionFromDataBase(Persistence.createEntityManagerFactory("find"), options);
        System.out.println(eachForForOptions);
        model.addObject("progress", eachForForOptions);
        model.addObject("votes", eachForForOptions.get(eachForForOptions.size() - 1));
        
        List<Vote> afterInsertingVote = DataBaseAccess.getVoteFromDataBase(Persistence.createEntityManagerFactory("find"), Long.parseLong(request.getParameter("user_id")), Long.parseLong(request.getParameter("question_id")));    
        List<String> progressColors = options.stream()
            .map(e -> (e.getOption_id() == afterInsertingVote.get(0).getOption_id()) ? "bg-success": "")
            .collect(Collectors.toCollection(ArrayList::new));

        model.addObject("progress_colors", progressColors);
        model.addObject("username", request.getParameter("get_username"));

        return model;
    }

    @RequestMapping(value="/dashboard", method = RequestMethod.POST)
    public ModelAndView backToDashBoard(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = DataBaseAccess.getListQuestions(Persistence.createEntityManagerFactory("find"));
        ModelAndView model = new ModelAndView(); 
        model.setViewName("account.jsp");
        model.addObject("listpolls", questions);
        model.addObject("user_id", request.getParameter("user_id"));
        model.addObject("username",request.getParameter("username"));
        return model;
    }

    @RequestMapping("/get_comment/{comment}/{id}/{username}")
    @ResponseBody
    public String enterComment(@PathVariable String comment, @PathVariable String id, @PathVariable String username) {
        DataBaseAccess.insertCommentsIntoDataBase(Long.parseLong(id), username, comment);
        return "success";
    }

    @RequestMapping("/get_comment/{id}")
    @ResponseBody
    public List<Comment> getComment(@PathVariable String id) {
        List<Comment> comments = DataBaseAccess.getCommentFromDataBaseByQuestionId(Persistence.createEntityManagerFactory("find"), Long.parseLong(id));
        return comments;
    }

    @RequestMapping("/get_search/{search}/{userId}/{username}")
    @ResponseBody
    public List<QuestionMapper> searchQuestion(@PathVariable String search, @PathVariable String userId, @PathVariable String username) {
        if(search.equals("all")) {
            List<Question> questions = DataBaseAccess.getListQuestions(Persistence.createEntityManagerFactory("find"));
            return questions.stream()
                .map(e -> new QuestionMapper(e.getQuestion(), e.getQuestion_id(), Long.parseLong(userId), username))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        }else {
            List<Question> questions = DataBaseAccess.searchQuestion(Persistence.createEntityManagerFactory("find"), search);
            return questions.stream()
                .map(e -> new QuestionMapper(e.getQuestion(), e.getQuestion_id(), Long.parseLong(userId), username))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        }

    }
}
