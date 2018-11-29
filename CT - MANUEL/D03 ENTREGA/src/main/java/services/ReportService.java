package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReportRepository;

import domain.Report;


@Service
@Transactional
public class ReportService {
	
	@Autowired
	private ReportRepository reportRepository;
	
	public Report create(){
		return new Report();	
	}
	public Collection<Report> findAll(){
		return reportRepository.findAll();
	}
	
	public Report findOne(int reportId){
		return reportRepository.findOne(reportId);
	}
	
	public Report save(Report report){
		return reportRepository.save(report);
	}
	
	public void delete(Report report){
		reportRepository.delete(report);
	}
	

}
