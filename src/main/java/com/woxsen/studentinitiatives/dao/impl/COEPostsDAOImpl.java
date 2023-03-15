package com.woxsen.studentinitiatives.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Repository;

import com.woxsen.studentinitiatives.dao.COEPostsDAO;
import com.woxsen.studentinitiatives.entities.COE;
import com.woxsen.studentinitiatives.entities.COEPosts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class COEPostsDAOImpl implements COEPostsDAO {

	private EntityManager entityManager;
	
	
	@Autowired
	public COEPostsDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public int addCOEPost(COEPosts coePost) {
		
		Session session = entityManager.unwrap(Session.class);
		
		session.persist(coePost);
		
		return coePost.getId();
		
	}

	@Override
	public COEPosts findById(int coePostId) {
		Session session = entityManager.unwrap(Session.class);
		
		COEPosts coePost = session.get(COEPosts.class, coePostId);
		
		if(coePost == null) throw new EntityNotFoundException("No post with id="+coePostId+ " was found");
		
		return coePost;
	}

	@Override
	public List<COEPosts> findByCOEId(int coeId) {
		Session session = entityManager.unwrap(Session.class);
		
		COE coe = session.get(COE.class, coeId);
		if(coe == null) throw new EntityNotFoundException("COE with coeId = "+coeId + " was not found");
		
		SelectionQuery<COEPosts> q = session.createSelectionQuery("from COEPosts c where c.coeId=:p", COEPosts.class);
		
		q.setParameter("q", coe);
		
		List<COEPosts> coePosts = q.getResultList();
		
		return coePosts;
		
	}

	@Override
	public List<COEPosts> findAll() {
		Session session = entityManager.unwrap(Session.class);
		
		List<COEPosts> coePosts = session.createSelectionQuery("from COEPosts", COEPosts.class).getResultList();
		
		return coePosts;
	}

	@Override
	public COEPosts patchCOEPost(COEPosts coePost) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCOEPostById(int coeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStreamResource getImage(int coeId, int coePostId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveImage(int coeId, int coePostId) {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStreamResource getFile(int coeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
