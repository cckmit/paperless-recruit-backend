package com.xiaohuashifu.recruit.external.service.pojo.dto;

public class DailyVisitTrendDTO {

	private String ref_date;
	private Integer session_cnt;
	private Integer visit_pv;
	private Integer visit_uv;
	private Integer visit_uv_new;
	private Float stay_time_uv;
	private Float stay_time_session;
	private Float visit_depth;
	
	public DailyVisitTrendDTO() {}
	
	public DailyVisitTrendDTO(String ref_date, Integer session_cnt, Integer visit_pv, Integer visit_uv,
			Integer visit_uv_new, Float stay_time_uv, Float stay_time_session, Float visit_depth) {
		this.ref_date = ref_date;
		this.session_cnt = session_cnt;
		this.visit_pv = visit_pv;
		this.visit_uv = visit_uv;
		this.visit_uv_new = visit_uv_new;
		this.stay_time_uv = stay_time_uv;
		this.stay_time_session = stay_time_session;
		this.visit_depth = visit_depth;
	}

	public String getRef_date() {
		return ref_date;
	}
	
	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}
	
	public Integer getSession_cnt() {
		return session_cnt;
	}
	
	public void setSession_cnt(Integer session_cnt) {
		this.session_cnt = session_cnt;
	}
	
	public Integer getVisit_pv() {
		return visit_pv;
	}
	
	public void setVisit_pv(Integer visit_pv) {
		this.visit_pv = visit_pv;
	}
	
	public Integer getVisit_uv() {
		return visit_uv;
	}
	
	public void setVisit_uv(Integer visit_uv) {
		this.visit_uv = visit_uv;
	}
	
	public Integer getVisit_uv_new() {
		return visit_uv_new;
	}
	
	public void setVisit_uv_new(Integer visit_uv_new) {
		this.visit_uv_new = visit_uv_new;
	}
	
	public Float getStay_time_uv() {
		return stay_time_uv;
	}
	
	public void setStay_time_uv(Float stay_time_uv) {
		this.stay_time_uv = stay_time_uv;
	}
	
	public Float getStay_time_session() {
		return stay_time_session;
	}
	
	public void setStay_time_session(Float stay_time_session) {
		this.stay_time_session = stay_time_session;
	}
	
	public Float getVisit_depth() {
		return visit_depth;
	}
	
	public void setVisit_depth(Float visit_depth) {
		this.visit_depth = visit_depth;
	}

	@Override
	public String toString() {
		return "DailyVisitTrendDTO [ref_date=" + ref_date + 
				", session_cnt=" + session_cnt + 
				", visit_pv=" + visit_pv + 
				", visit_uv=" + visit_uv + 
				", visit_uv_new=" + visit_uv_new + 
				", stay_time_uv=" + stay_time_uv + 
				", stay_time_session=" + stay_time_session + 
				", visit_depth=" + visit_depth + "]";
	}
	
}
