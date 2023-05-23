package com.lzh.blog.service.Impl;

import com.lzh.blog.dao.CommentRepository;
import com.lzh.blog.entity.Comment;
import com.lzh.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> listComment(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级评论节点
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        CombineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments 子评论
     */
    private void CombineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> reply1 = comment.getReplyComments();
            for (Comment reply : reply1){
                recursively(reply);
            }
            //修改集合为迭代后的集合
            comment.setReplyComments(temp);
            //清除临时存放区
            temp = new ArrayList<>();
        }
    }


    //存放迭代出的评论子集
    private List<Comment> temp = new ArrayList<>();
    /**
     * 递归迭代 查询评论子集
     * @param comment
     */
    private void recursively(Comment comment){
        temp.add(comment); //顶节点暂时存储
        if (comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
//                temp.add(reply);
//                if (reply.getReplyComments().size()>0){
                    recursively(reply);
//                }
            }
        }
    }
}
