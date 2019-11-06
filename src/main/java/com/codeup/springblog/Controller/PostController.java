package com.codeup.springblog.Controller;

import com.codeup.springblog.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    ArrayList<Post> postList;

    public PostController() {

        postList = new ArrayList<>();

        postList.add(new Post(1,"First post","Pieces of Eight cackle fruit weigh anchor line Arr main sheet heave down jury mast. Cable Davy Jones' Locker Arr Sail ho prow crimp fathom belaying pin. Spanker hogshead bowsprit smartly gally aye keelhaul Jack Ketch. Crimp quarter scurvy black spot lad log chase guns parley. Gabion chantey Admiral of the Black topgallant Gold Road gangplank Jack Tar wherry. Nipper lad Pirate Round scallywag topgallant line furl crack Jennys tea cup. Deadlights code of conduct strike colors transom interloper scurvy bilge mizzen. Brig ballast capstan maroon keel weigh anchor boom American Main. Spanker Pirate Round trysail bucko overhaul coxswain chantey fluke. Fluke Nelsons folly pirate pillage lookout doubloon gibbet driver."));
        postList.add(new Post(2,"Second post","Switzerland is small and neutral! We are more like Germany, ambitious and misunderstood! It's just like the story of the grasshopper and the octopus. All year long, the grasshopper kept burying acorns for winter, while the octopus mooched off his girlfriend and watched TV. But then the winter came, and the grasshopper died, and the octopus ate all his acorns. Also he got a race car. Is any of this getting through to you?"));
        postList.add(new Post(3,"Third post","3 wolf moon woke blue bottle, blog ennui retro messenger bag taxidermy 90's waistcoat. Flannel locavore selfies artisan freegan microdosing coloring book next level four dollar toast twee chartreuse listicle sartorial. Man braid stumptown brooklyn drinking vinegar fingerstache thundercats flexitarian farm-to-table. Ethical gochujang scenester put a bird on it organic succulents. Lo-fi pork belly ugh seitan yuccie humblebrag hashtag glossier food truck cliche."));
    }

    @GetMapping("/posts")
    public String index(Model vModel) {
        vModel.addAttribute("posts", postList);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model vModel) {
        vModel.addAttribute("post", postList.get((int)id-1));
        return "posts/show";
    }


    @GetMapping("/posts/create")
    @ResponseBody
    public String showForm() {
        return "view the form for creating an ad";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String create(@RequestParam String title, @RequestParam String body) {
        System.out.println("title = " + title);
        System.out.println("body = " + body);
        return "create a new ad";
    }
}
