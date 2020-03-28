package io.javabrains.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CatalogItem {
	//appel au  movie api
	String name;
	String desc;
	//appel   Rating api
	int rating;

}
