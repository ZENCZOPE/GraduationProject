package com.test;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cms.pojo.Game;
import com.cms.service.GameService;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class Test1 {

	@Resource
	GameService gameService;

	@Test
	public void test() throws SQLException {

	}

	@Test
	public void test2() throws SQLException {
		Game game = new Game();
		game.setGameId(2);

		try {
			// gameService.makeGame(game);
			gameService.clearPlayerAndJudge(game);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test3() throws Exception {
		// billService.updateAmount();

	}

	public static void main(String[] args) {
		String level = "5.5";
		String endString = level.substring(1);
		// int a=Integer.parseInt(level);
		float b = Float.parseFloat(level);
		System.out.println(endString);
	}
}
