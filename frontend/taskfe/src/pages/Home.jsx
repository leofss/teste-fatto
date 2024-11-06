import React from "react";
import TaskList from "../components/TaskList";
import Hero from "../components/Hero";

function Home() {
  return (
    <>
      <Hero
        title="Fatto Technical Test - Todo List"
        subtitle="Developed with Java Spring Boot + ReactJS"
      />
      <TaskList />      
    </>
  );
}

export default Home;
