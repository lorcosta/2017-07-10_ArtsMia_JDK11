package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects(Map<Integer,ArtObject> idMapObject) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				if(!idMapObject.containsKey(artObj.getId())) {
					idMapObject.put(artObj.getId(), artObj);
				}
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer,ArtObject> idMapObject){
		String sql="SELECT eo1.object_id AS o1, eo2.object_id AS o2, COUNT(*) AS peso " + 
				"FROM exhibition_objects eo1, exhibition_objects eo2 " + 
				"WHERE eo1.exhibition_id=eo2.exhibition_id AND eo1.object_id>eo2.object_id " + 
				"GROUP BY eo1.object_id, eo2.object_id";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Adiacenza a=new Adiacenza(idMapObject.get(res.getInt("o1")),idMapObject.get(res.getInt("o2")),res.getDouble("peso"));
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean test(Integer objectId) {
		String sql="SELECT * " + 
				"FROM objects " + 
				"WHERE object_id=?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, objectId);
			ResultSet res = st.executeQuery();
			if(res.next()) {
				conn.close();
				return true;
			}
			conn.close();
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
