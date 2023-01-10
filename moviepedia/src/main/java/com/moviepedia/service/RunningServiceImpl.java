package com.moviepedia.service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class RunningServiceImpl implements RunningService{
	@Autowired
	private ManageService manageService;

	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

	
	
}

