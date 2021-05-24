package com.hotel.controller.food;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.controller.BasicController;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.service.food.HotelFoodsService;

@RequestMapping("searchFood")
@Controller
public class SearchHotelFoodsController extends BasicController{

    private static Logger logger = LogManager.getLogger(SearchHotelFoodsController.class);
	
	@Autowired
	HotelFoodsService hotelFoodsService;
	@RequestMapping("")
	public String Index(HttpServletRequest request) {
		return "food/searchfoods";
	}
	
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> queryOnePage(HttpServletRequest request) {
		InitDataTablesParams(request);
		Page<HotelFoods> result =hotelFoodsService.queryOnePage(this.getPageIndex(),this.getPageRow(),this.getSort());
		Map<String, Object> mapresult=getResultMap(result);
		
		return mapresult;
	}
}
